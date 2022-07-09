package com.ping.easyfile.excelmeta;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author liu_wp
 * @date Created in 2019/3/5 18:53
 */
public class ExcelStyle {
    private ExcelFont excelFont;
    private IndexedColors excelBackGroundColor;
    private CellStyle currentCellStyle;
    private CellStyle defaultCellStyle;

    public ExcelFont getExcelFont() {
        return excelFont;
    }

    public void setExcelFont(ExcelFont excelFont) {
        this.excelFont = excelFont;
    }

    public IndexedColors getExcelBackGroundColor() {
        return excelBackGroundColor;
    }

    public void setExcelBackGroundColor(IndexedColors excelBackGroundColor) {
        this.excelBackGroundColor = excelBackGroundColor;
    }

    public CellStyle getCurrentCellStyle() {
        return currentCellStyle;
    }

    public void setCurrentCellStyle(CellStyle currentCellStyle) {
        this.currentCellStyle = currentCellStyle;
    }

    public CellStyle getDefaultCellStyle() {
        return defaultCellStyle;
    }

    public void setDefaultCellStyle(CellStyle defaultCellStyle) {
        this.defaultCellStyle = defaultCellStyle;
    }
}
