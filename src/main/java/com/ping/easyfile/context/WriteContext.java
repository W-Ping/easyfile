package com.ping.easyfile.context;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.em.BorderEnum;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.em.TableBodyEnum;
import com.ping.easyfile.excelmeta.*;
import com.ping.easyfile.util.StyleUtil;
import com.ping.easyfile.util.WorkBookUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:00
 * @see
 */
public class WriteContext {
    private Workbook workbook;
    private Sheet currentSheet;
    private String currentSheetName;
    private ExcelSheet currentExcelSheetParam;
    private ExcelTypeEnum excelType;
    private OutputStream outputStream;

    public WriteContext(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum excelType) throws IOException {
        this.excelType = excelType;
        this.outputStream = outputStream;
        this.workbook = WorkBookUtil.createWorkBook(templateInputStream, excelType);
    }

    public void currentSheet(ExcelSheet excelSheet) {
        if (null == currentExcelSheetParam || currentExcelSheetParam.getSheetNo() != excelSheet.getSheetNo()) {
            cleanCurrentSheet();
            currentExcelSheetParam = excelSheet;
            currentSheetName = excelSheet.getSheetName();
            try {
                this.currentSheet = workbook.getSheetAt(excelSheet.getSheetNo());
            } catch (Exception e) {
                this.currentSheet = WorkBookUtil.createSheet(workbook, excelSheet);
            }
            StyleUtil.buildTableWidthStyle(this.getCurrentSheet(), this.currentExcelSheetParam.getColumnWidthMap());
            List<ExcelTable> excelTables = excelSheet.getExcelTables();
            if (!CollectionUtils.isEmpty(excelTables)) {
                excelTables.stream().forEach(v -> initTableProperties(v));
            }
            excelSheet.initCurrentSheet();
        }

    }

    /**
     * init table
     *
     * @param table
     */
    public void initTable(ExcelTable table) {
        initTableHeadStyle(table);
        initTableContentStyle(table);
        initTableHead(table);
    }

    private void initTableProperties(ExcelTable table) {
        initExcelHeadProperty(table);
        initTableRange(table);
    }

    private void initExcelHeadProperty(ExcelTable table) {
        table.setExcelHeadProperty(new ExcelHeadProperty(table.getHeadClass(), table.getHead()));
        if (CollectionUtils.isEmpty(table.getHead())) {
            table.setHead(table.getExcelHeadProperty().getHead());
        }
    }

    private void initTableRange(ExcelTable table) {
        int headRowNum = table.isNeedHead() ? table.getExcelHeadProperty().getHeadRowNum() : 0;
        table.setStartContentRowIndex(table.getFirstRowIndex() + headRowNum);
        int lastRow = table.getStartContentRowIndex() + (!CollectionUtils.isEmpty(table.getData()) ? table.getData().size() : FileConstant.DEFAULT_ROW);
        table.setTableCellRange(new ExcelCellRange(table.getFirstRowIndex(), lastRow, table.getFirstCellIndex(), table.getHead().size() + table.getFirstCellIndex()));

    }


    private void initTableHead(ExcelTable table) {
        ExcelHeadProperty excelHeadProperty = table.getExcelHeadProperty();
        ExcelStyle headStyle = excelHeadProperty.getHeadStyle();
        if (table.isNeedHead() && excelHeadProperty != null && excelHeadProperty.getHead().size() > 0) {
            int startRow = table.getFirstRowIndex();
            try {
                addMergedRegionToCurrentSheet(startRow, table.getFirstCellIndex(), table.getExcelHeadProperty());
            } catch (Exception e) {
                e.printStackTrace();
            }
            int rowNum = excelHeadProperty.getRowNum();
            for (int i = startRow; i < rowNum + startRow; i++) {
                Row row = WorkBookUtil.createOrGetRow(currentSheet, i);
                if (headStyle != null) {
                    StyleUtil.buildCellBorderStyle(headStyle.getCurrentCellStyle(), startRow, i == startRow, BorderEnum.TOP);
                }
                addOneRowOfHeadDataToExcel(row, excelHeadProperty.getHeadByRowNum(i - startRow), table.getFirstCellIndex(), headStyle);
            }
        }
    }

    /**
     * table 头样式
     *
     * @param table
     */
    private void initTableHeadStyle(ExcelTable table) {
        ExcelHeadProperty excelHeadProperty = table.getExcelHeadProperty();
        ExcelStyle headStyle = excelHeadProperty.getHeadStyle();
        CellStyle cellStyle = null;
        if (headStyle != null) {
            cellStyle = StyleUtil.buildCellStyle(this.workbook, headStyle.getExcelFont(),
                    headStyle.getExcelBackGroundColor(), TableBodyEnum.HEAD);
        } else {
            headStyle = new ExcelStyle();
            cellStyle = StyleUtil.defaultHeadStyle(this.workbook);
        }
        headStyle.setCurrentCellStyle(cellStyle);
        table.setTableHeadStyle(headStyle);
    }

    /**
     * table 内容样式
     *
     * @param table
     */
    private void initTableContentStyle(ExcelTable table) {
        ExcelStyle tableStyle = table.getTableStyle();
        CellStyle cellStyle = null;
        if (tableStyle != null) {
            cellStyle = StyleUtil.buildCellStyle(this.workbook, tableStyle.getExcelFont(),
                    tableStyle.getExcelBackGroundColor(), TableBodyEnum.CONTENT);
            tableStyle.setCurrentCellStyle(cellStyle);
        } else {
            tableStyle = new ExcelStyle();
            cellStyle = StyleUtil.defaultContentCellStyle(this.workbook);
            tableStyle.setDefaultCellStyle(cellStyle);
        }
        tableStyle.setCurrentCellStyle(cellStyle);
        table.setTableStyle(tableStyle);
    }

    private void addOneRowOfHeadDataToExcel(Row row, List<String> headByRowNum, int startCellIndex, ExcelStyle tableStyle) {
        CellStyle currentCellStyle = null;
        if (tableStyle != null) {
            currentCellStyle = tableStyle.getCurrentCellStyle();
        }
        if (headByRowNum != null && headByRowNum.size() > 0) {
            int lastIndex = headByRowNum.size() - 1;
            for (int i = 0; i < headByRowNum.size(); i++) {
                StyleUtil.buildCellBorderStyle(currentCellStyle, 0, 0 == i, BorderEnum.LEFT);
                StyleUtil.buildCellBorderStyle(currentCellStyle, lastIndex, lastIndex == i, BorderEnum.RIGHT);
                WorkBookUtil.createCell(row, startCellIndex + i, currentCellStyle, headByRowNum.get(i));
            }

        }
    }

    private void addMergedRegionToCurrentSheet(int startRow, int startCell, ExcelHeadProperty excelHeadProperty) {
        for (ExcelCellRange cellRangeModel : excelHeadProperty.getCellRangeModels(startCell)) {
            currentSheet.addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRowIndex() + startRow,
                    cellRangeModel.getLastRowIndex() + startRow,
                    cellRangeModel.getFirstCellIndex(), cellRangeModel.getLastCellIndex()));

        }
    }

    private void cleanCurrentSheet() {
        this.currentSheet = null;
        this.currentSheetName = null;
        this.currentExcelSheetParam = null;

    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public String getCurrentSheetName() {
        return currentSheetName;
    }

    public void setCurrentSheetName(String currentSheetName) {
        this.currentSheetName = currentSheetName;
    }

    public ExcelSheet getCurrentExcelSheetParam() {
        return currentExcelSheetParam;
    }

    public void setCurrentExcelSheetParam(ExcelSheet currentExcelSheetParam) {
        this.currentExcelSheetParam = currentExcelSheetParam;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

}
