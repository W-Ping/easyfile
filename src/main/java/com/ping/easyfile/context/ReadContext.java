package com.ping.easyfile.context;

import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelCellRange;
import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.exception.ExcelParseException;
import com.ping.easyfile.util.WorkBookUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 16:59
 * @see
 */
public class ReadContext {
    private Workbook workbook;
    private Sheet currentSheet;
    private ExcelTypeEnum excelType;

    public ReadContext(InputStream inputStream) throws IOException {
        this.excelType = ExcelTypeEnum.valueOf(inputStream);
        this.workbook = WorkBookUtil.createWorkBook(inputStream, this.excelType);
    }

    public List<Object> readExcel(ExcelReadTable excelReadTable) throws ExcelParseException {
        List<Object> list = new ArrayList<>();
        this.currentSheet = this.workbook.getSheetAt(excelReadTable.getSheetNo());
        if (null == this.currentSheet) {
            throw new ExcelParseException("sheet is null");
        }
        ExcelCellRange excelCellRange = excelReadTable.getExcelCellRange();
        int firstRowIndex = excelCellRange.getFirstRowIndex();
        int firstCellIndex = excelCellRange.getFirstCellIndex();
        int lastCellIndex = excelCellRange.getLastCellIndex();
        do {
            Row row = this.currentSheet.getRow(firstRowIndex);
            if (row == null) {
                break;
            }
            for (int i = firstCellIndex; i < lastCellIndex; i++) {
                Cell cell = row.getCell(i);
                String stringCellValue = cell.getStringCellValue();
            }
            firstRowIndex++;
        } while (true);
        return list;
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

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }
}
