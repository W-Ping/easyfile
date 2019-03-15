package com.ping.easyfile.core.excel;

import com.ping.easyfile.context.ExportContext;
import com.ping.easyfile.core.handler.IExportAfterHandler;
import com.ping.easyfile.em.BorderEnum;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.*;
import com.ping.easyfile.exception.ExcelParseException;
import com.ping.easyfile.util.StyleUtil;
import com.ping.easyfile.util.TypeUtil;
import com.ping.easyfile.util.WorkBookUtil;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:22
 * @see
 */
public class ExcelBuilderImpl implements IExcelBuilder {
    private ExportContext context;

    public ExcelBuilderImpl(InputStream templateInputStream,
                            OutputStream outputStream,
                            ExcelTypeEnum excelType, IExportAfterHandler iExportAfterHandler) {
        try {
            context = new ExportContext(templateInputStream, outputStream, excelType, iExportAfterHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelBuilderImpl(InputStream templateInputStream,
                            OutputStream outputStream,
                            ExcelTypeEnum excelType) {
        this(templateInputStream, outputStream, excelType, null);
    }

    @Override
    public void addContent(ExcelSheet excelSheet) {
        context.currentSheet(excelSheet);
        List<ExcelTable> excelTables = excelSheet.getExcelTables();
        for (ExcelTable excelTable : excelTables) {
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
        for (int i = 0; i < data.size(); i++) {
            int n = i + startRow;
            addOneRowDataToExcel(data.get(i), n, table.getFirstCellIndex(), currentCellStyle, table.getExcelHeadProperty());
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
        } catch (IOException e) {
            throw new ExcelParseException("Excel IO error", e);
        }
    }

    private void addOneRowDataToExcel(Object oneRowData, int n, int startCellIndex, CellStyle cellStyle, ExcelHeadProperty excelHeadProperty) {
        Row row = WorkBookUtil.createOrGetRow(context.getCurrentSheet(), n);
        if (context.getiExportAfterHandler() != null) {
            context.getiExportAfterHandler().row(n, row);
        }
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List) oneRowData, row, cellStyle, startCellIndex);
        } else {
            addObjectToExcel(oneRowData, row, cellStyle, startCellIndex, excelHeadProperty);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row, CellStyle cellStyle, int startCellIndex) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        for (int i = 0; i < oneRowData.size(); i++) {
            Object cellValue = oneRowData.get(i);
            Cell cell = WorkBookUtil.createCell(row, i + startCellIndex, cellStyle, cellValue,
                    TypeUtil.isNum(cellValue));
            if (context.getiExportAfterHandler() != null) {
                context.getiExportAfterHandler().cell(i, cell);
            }
        }
    }

    private void addObjectToExcel(Object oneRowData, Row row, CellStyle cellStyle, int startCellIndex, ExcelHeadProperty excelHeadProperty) {
        BeanMap beanMap = BeanMap.create(oneRowData);
        List<ExcelColumnProperty> columnPropertyList = excelHeadProperty.getColumnPropertyList();
        if (!CollectionUtils.isEmpty(columnPropertyList)) {
            int lastIndex = columnPropertyList.size() - 1;
            for (int i = 0; i < columnPropertyList.size(); i++) {
                BaseRowModel baseRowModel = (BaseRowModel) oneRowData;
                ExcelColumnProperty excelColumnProperty = columnPropertyList.get(i);
                String cellValue = TypeUtil.getFieldStringValue(beanMap, excelColumnProperty.getField().getName(),
                        excelColumnProperty.getFormat());
                cellStyle = baseRowModel.getStyle(i) != null ? baseRowModel.getStyle(i)
                        : cellStyle;
                StyleUtil.buildCellBorderStyle(cellStyle, 0, 0 == i, BorderEnum.LEFT);
                StyleUtil.buildCellBorderStyle(cellStyle, lastIndex, lastIndex == i, BorderEnum.RIGHT);
                Cell cell = WorkBookUtil.createCell(row, i + startCellIndex, cellStyle, cellValue,
                        TypeUtil.isNum(excelColumnProperty.getField()));
                if (context.getiExportAfterHandler() != null) {
                    context.getiExportAfterHandler().cell(i, cell);
                }
            }
        }
    }
}
