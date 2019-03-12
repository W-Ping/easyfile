package com.ping.easyfile.util;

import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class WorkBookUtil {

    public static void createTempExcel(String filePath, ExcelTypeEnum excelType) {
        try {
            Workbook workBook = createWorkBook(null, excelType);
            FileOutputStream out = new FileOutputStream(filePath);
            workBook.write(out);
            out.close();
        } catch (Exception e) {

        }
    }

    public static Workbook createWorkBook(InputStream templateInputStream, ExcelTypeEnum excelType) throws IOException {
        Workbook workbook;
        if (ExcelTypeEnum.XLS.equals(excelType)) {
            workbook = (templateInputStream == null) ? new HSSFWorkbook() : new HSSFWorkbook(
                    new POIFSFileSystem(templateInputStream));
        } else {
            workbook = (templateInputStream == null) ? new SXSSFWorkbook(500) : new SXSSFWorkbook(
                    new XSSFWorkbook(templateInputStream));
        }
        return workbook;
    }

    public static Sheet createOrGetSheet(Workbook workbook, int sheetAt) {
        Sheet sheet = null;
        try {
            sheet = workbook.getSheetAt(sheetAt);
            if (sheet == null) {
                sheet = workbook.createSheet();
            }
        } catch (Exception e) {
            throw new RuntimeException("constructCurrentSheet error", e);
        }
        return sheet;
    }

    public static Row createOrGetRow(Sheet sheet, int rowNum) {
        Row row = null;
        try {
            row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
        } catch (Exception e) {
            row = sheet.createRow(rowNum);
        }
        return row;
    }

    public static Row createRow(Sheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle, String cellValue) {
        return createCell(row, colNum, cellStyle, cellValue, false);
    }

    public static Sheet createSheet(Workbook workbook, ExcelSheet excelSheet) {
        return workbook.createSheet(excelSheet.getSheetName() != null ? excelSheet.getSheetName() : excelSheet.getSheetNo() + "");
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle, Object cellValue, Boolean isNum) {
        Cell cell = row.createCell(colNum);
        cell.setCellStyle(cellStyle);
        if (null != cellValue) {
            if (isNum) {
                cell.setCellValue(Double.parseDouble(cellValue.toString()));
            } else {
                cell.setCellValue(cellValue.toString());
            }
            cell.getCellStyle();
        }
        return cell;
    }

    public static void main(String[] args) {
//        BigDecimal d = new BigDecimal(12.3388);
        float d = 66.2f;
        Object obj = d;
        Class<?> aClass = obj.getClass();
//        BigDecimal dc = (BigDecimal) d.setScale(2, BigDecimal.ROUND_DOWN);
        System.out.println(obj instanceof Float);
//        System.out.println(dc.toPlainString());
    }
}
