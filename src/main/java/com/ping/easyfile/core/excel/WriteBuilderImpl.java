package com.ping.easyfile.core.excel;

import com.ping.easyfile.context.WriteContext;
import com.ping.easyfile.em.BorderEnum;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.*;
import com.ping.easyfile.exception.ExcelParseException;
import com.ping.easyfile.request.ExcelWriteParam;
import com.ping.easyfile.util.ExcelWaterUtil;
import com.ping.easyfile.util.StyleUtil;
import com.ping.easyfile.util.TypeUtil;
import com.ping.easyfile.util.WorkBookUtil;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:22
 */
public class WriteBuilderImpl implements IWriteBuilder {
    private WriteContext context;
    private Map<Integer, CellStyle> cellStyleMap;

    public WriteBuilderImpl(InputStream templateInputStream,
                            OutputStream outputStream,
                            ExcelTypeEnum excelType, ExcelWriteParam excelWriteParam) throws IOException {
        context = new WriteContext(templateInputStream, outputStream, excelType, excelWriteParam);

    }

    @Override
    public void addContent(ExcelSheet excelSheet) {
        context.currentSheet(excelSheet);
        List<ExcelTable> excelTables = excelSheet.getExcelTables();
        for (ExcelTable excelTable : excelTables) {
            cellStyleMap = null;
            context.initTable(excelTable);
            this.addContent(excelTable);
        }
        if (excelSheet.getMergeData() != null) {
            excelSheet.getMergeData().stream().forEach(v -> merge(v[0], v[1], v[2], v[3]));
        }

    }

    @Override
    public void addContent(ExcelTable table) {
        List<? extends BaseRowModel> data = table.getData();
        int startRow = table.getStartContentRowIndex();
        ExcelStyle tableStyle = table.getTableStyle();
        CellStyle currentCellStyle = null;
        if (tableStyle != null) {
            currentCellStyle = tableStyle.getCurrentCellStyle();
        }
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        //导出前置处理接口
        if (table.getiWriteBeforeHandler() != null) {
            cellStyleMap = table.getiWriteBeforeHandler().initCellStyle(context.getWorkbook());
        }
        for (int i = 0; i < data.size(); i++) {
            int n = i + startRow;
            addOneRowDataToExcel(data.get(i), n, currentCellStyle, table);
            StyleUtil.buildCellBorderStyle(currentCellStyle, 0, i == 0 && !table.isNeedHead(), BorderEnum.TOP);
        }
    }

    /**
     * @param startRow
     * @param endRow
     * @param startCell
     * @param endCell
     */
    @Override
    public void merge(int startRow, int endRow, int startCell, int endCell) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, startCell, endCell);
        context.getCurrentSheet().addMergedRegion(cellRangeAddress);
    }

    @Override
    public void finish() {
        try {
            context.getWorkbook().write(context.getOutputStream());
            context.getWorkbook().close();
        } catch (Exception e) {
            throw new ExcelParseException("Excel IO error", e);
        }
    }

    @Override
    public WriteContext getWriteContext() {
        return context;
    }

    @Override
    public void beforeWrite() {
        //添加水印
        if (context.getExcelWriteParam().getWaterMark() != null) {
            if (context.getWorkbook() instanceof XSSFWorkbook) {
                try {
                    ExcelWaterUtil.putWaterMarkTextToXlsx((XSSFWorkbook) context.getWorkbook(), context.getExcelWriteParam().getWaterMark());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void addOneRowDataToExcel(Object oneRowData, int n, CellStyle cellStyle, ExcelTable excelTable) {
        Row row = WorkBookUtil.createOrGetRow(context.getCurrentSheet(), n);
        if (excelTable.getiWriteAfterHandler() != null) {
            excelTable.getiWriteAfterHandler().row(n, row);
        }
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List) oneRowData, row, cellStyle, excelTable);
        } else {
            addObjectToExcel(oneRowData, row, cellStyle, excelTable);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row, CellStyle cellStyle, ExcelTable excelTable) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        for (int i = 0; i < oneRowData.size(); i++) {
            Object cellValue = oneRowData.get(i);
            Cell cell = WorkBookUtil.createCell(row, i + excelTable.getFirstCellIndex(), cellStyle, cellValue,
                    TypeUtil.isNum(cellValue));
            if (excelTable.getiWriteAfterHandler() != null) {
                excelTable.getiWriteAfterHandler().cell(i, cell);
            }
        }
    }

    private void addObjectToExcel(Object oneRowData, Row row, CellStyle cellStyle, ExcelTable excelTable) {
        int startCellIndex = excelTable.getFirstCellIndex();
        BeanMap beanMap = BeanMap.create(oneRowData);
        List<ExcelColumnProperty> columnPropertyList = excelTable.getExcelHeadProperty().getColumnPropertyList();
        if (!CollectionUtils.isEmpty(columnPropertyList)) {
            int lastIndex = columnPropertyList.size() - 1;
            for (int i = 0; i < columnPropertyList.size(); i++) {
//                BaseRowModel baseRowModel = (BaseRowModel) oneRowData;
                ExcelColumnProperty excelColumnProperty = columnPropertyList.get(i);
                String cellValue = TypeUtil.getFieldStringValue(beanMap, excelColumnProperty.getField().getName(),
                        excelColumnProperty.getFormat());
                StyleUtil.buildCellBorderStyle(cellStyle, 0, 0 == i, BorderEnum.LEFT);
                StyleUtil.buildCellBorderStyle(cellStyle, lastIndex, lastIndex == i, BorderEnum.RIGHT);
                CellStyle cellStyle1 = cellStyle;
                if (null != cellStyleMap && cellStyleMap.containsKey(i + startCellIndex)) {
                    cellStyle1 = cellStyleMap.get(i + startCellIndex);
                }
                Cell cell = WorkBookUtil.createCell(row, i + startCellIndex, cellStyle1, cellValue,
                        TypeUtil.isNum(excelColumnProperty.getField()));
                if (excelTable.getiWriteAfterHandler() != null) {
                    excelTable.getiWriteAfterHandler().cell(i, cell);
                }
            }
        }
    }
}
