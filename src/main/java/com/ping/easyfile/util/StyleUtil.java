package com.ping.easyfile.util;

import com.ping.easyfile.em.BorderEnum;
import com.ping.easyfile.em.TableBodyEnum;
import com.ping.easyfile.excelmeta.ExcelFont;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/8 10:29
 */
public class StyleUtil {
    /**
     * excel 表头默认样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle defaultHeadStyle(Workbook workbook) {
        CellStyle newCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        return newCellStyle;
    }

    /**
     * excel 内容默认样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle defaultContentCellStyle(Workbook workbook) {
        CellStyle newCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(false);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
//        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        newCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        return newCellStyle;
    }

    public static void buildCellBorderStyle(CellStyle cellStyle, int index, boolean bol, BorderEnum borderEnum) {
        if (cellStyle != null && bol) {
            BorderStyle borderBottomEnum = cellStyle.getBorderBottom();
            if (borderBottomEnum == null) {
                if (cellStyle.getBorderTop() != null) {
                    borderBottomEnum = cellStyle.getBorderTop();
                } else if (cellStyle.getBorderLeft() != null) {
                    borderBottomEnum = cellStyle.getBorderLeft();
                } else {
                    borderBottomEnum = cellStyle.getBorderRight();
                }
            }
            switch (borderEnum) {
                case TOP:
                    cellStyle.setBorderTop(borderBottomEnum);
                    break;
                case LEFT:
                    cellStyle.setBorderLeft(borderBottomEnum);
                    break;
                case BOTTOM:
                    cellStyle.setBorderBottom(borderBottomEnum);
                    break;
                case RIGHT:
                    cellStyle.setBorderRight(borderBottomEnum);
                    break;
                default:
                    break;
            }
        }
    }

    public static CellStyle defaultFootCellStyle(Workbook workbook) {
        CellStyle newCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 20);
        font.setBold(false);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        return newCellStyle;
    }

    /**
     * @param workbook
     * @param f
     * @param indexedColors
     * @return
     */
    public static CellStyle buildCellStyle(Workbook workbook, ExcelFont f,
                                           IndexedColors indexedColors, TableBodyEnum tableBodyEnum) {
        CellStyle cellStyle = null;
        if (TableBodyEnum.HEAD.equals(tableBodyEnum)) {
            cellStyle = defaultHeadStyle(workbook);
        } else if (TableBodyEnum.FOOT.equals(tableBodyEnum)) {
            cellStyle = defaultFootCellStyle(workbook);
        } else {
            cellStyle = defaultContentCellStyle(workbook);
        }
        if (f != null) {
            Font font = workbook.createFont();
            font.setFontName(f.getFontName());
            font.setFontHeightInPoints(f.getFontHeightInPoints());
            font.setBold(f.isBold());
            if (f.getFontColor() != null) {
                font.setColor(f.getFontColor().getIndex());
            }
            cellStyle.setFont(font);
        }
        if (indexedColors != null) {
            cellStyle.setFillForegroundColor(indexedColors.getIndex());
        }
        return cellStyle;
    }

    /**
     * @param currentSheet
     * @param sheetWidthMap
     * @return
     */
    public static Sheet buildTableWidthStyle(Sheet currentSheet, Map<Integer, Integer> sheetWidthMap) {
        currentSheet.setDefaultColumnWidth(15);
        for (Map.Entry<Integer, Integer> entry : sheetWidthMap.entrySet()) {
            currentSheet.setColumnWidth(entry.getKey(), (int) ((entry.getValue()) * 256));
        }
        return currentSheet;
    }


}
