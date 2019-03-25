package com.ping.easyfile.context;

import com.ping.easyfile.annotation.ExcelColumn;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.BaseRowModel;
import com.ping.easyfile.excelmeta.ExcelCellRange;
import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.exception.ExcelParseException;
import com.ping.easyfile.util.DateUtil;
import com.ping.easyfile.util.WorkBookUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        this.workbook = WorkBookUtil.createWorkBookWithStream(inputStream, this.excelType);
    }

    public List<Object> readExcelTable(ExcelReadTable excelReadTable) throws ExcelParseException {
        List<Object> list = new ArrayList<>();
        this.currentSheet = this.workbook.getSheetAt(excelReadTable.getSheetNo());
        if (null == this.currentSheet) {
            throw new ExcelParseException("sheet is null");
        }
        int startRowIndex = excelReadTable.getStartRowIndex();
        Integer lastRowIndex = excelReadTable.getLastRowIndex();
        int startCellIndex = excelReadTable.getStartCellIndex();
        Integer lastCellIndex = excelReadTable.getLastCellIndex();
        Class<? extends BaseRowModel> dataModelClass = excelReadTable.getDataModelClass();
        int physicalNumberOfRows = this.currentSheet.getPhysicalNumberOfRows();
        Object obj = null;
        do {
            Row row = this.currentSheet.getRow(startRowIndex);
            //table 循环结束条件
            if (row == null || (null != lastRowIndex && lastRowIndex == startRowIndex)) {
                break;
            }
            try {
                Class<?> aClass = Class.forName(dataModelClass.getName());
                obj = aClass.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            lastCellIndex = startCellIndex + (lastCellIndex != null ? lastCellIndex : row.getPhysicalNumberOfCells() - 1);
            for (int i = startCellIndex; i <= lastCellIndex; i++) {
                Cell cell = row.getCell(i);
                addExcelValueToObject(cell, obj);
            }
            list.add(obj);
            startRowIndex++;
        } while (startRowIndex <= physicalNumberOfRows);
        return list;
    }


    private void addExcelValueToObject(Cell cell, Object obj) {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        int columnIndex = cell.getColumnIndex();
        DataFormatter FORMATTER = new DataFormatter();
        String cellValue = FORMATTER.formatCellValue(cell);
        try {
            for (Field f : declaredFields) {
                f.setAccessible(true);
                ExcelColumn annotation = f.getAnnotation(ExcelColumn.class);
                if (annotation != null) {
                    int index = annotation.index();
                    if (columnIndex == index) {
                        CellType cellTypeEnum = cell.getCellTypeEnum();
                        String format = annotation.format();
                        if (Date.class.equals(f.getType()) && cellTypeEnum.equals(CellType.NUMERIC)
                                && org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell, null)) {
                            Date dateCellValue = cell.getDateCellValue();
                            if (StringUtils.isNotBlank(format)) {
                                String ss = DateUtil.formatDate(dateCellValue, format);
                                dateCellValue = DateUtil.covertStrToDate(ss);
                            } else {
                                String ss = DateUtil.formatDate(dateCellValue, "yyyy/MM/dd HH:mm:ss");
                                dateCellValue = DateUtil.covertStrToDate(ss);
                            }
                            f.set(obj, dateCellValue);

                        } else if (BigDecimal.class.equals(f.getType())) {
                            f.set(obj, new BigDecimal(cellValue));
                        } else if (Integer.class.equals(f.getType()) || int.class.equals(f.getType())) {
                            f.set(obj, Integer.valueOf(cellValue));
                        } else if (Double.class.equals(f.getType()) || double.class.equals(f.getType())) {
                            f.set(obj, Double.valueOf(cellValue));
                        } else if (Float.class.equals(f.getType()) || float.class.equals(f.getType())) {
                            f.set(obj, Float.valueOf(cellValue));
                        } else {
                            f.set(obj, cellValue);
                        }
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private boolean isDataFormat(Cell cell, String format) {
        short dataFormat = cell.getCellStyle().getDataFormat();
        return (short) BuiltinFormats.getBuiltinFormat(format) == dataFormat;
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
