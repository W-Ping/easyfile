package com.ping.easyfile.handler;

import com.ping.easyfile.core.handler.IWriteBeforHandler;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/29 15:55
 * @see
 */
public class WriteBeforHandler2Impl implements IWriteBeforHandler {
    @Override
    public Map<Integer, CellStyle> initCellStyle(Workbook workbook) {
        Map<Integer, CellStyle> map = new HashMap<>();
        CellStyle newCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 25);
        font.setBold(true);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        map.put(0, newCellStyle);
        map.put(2, newCellStyle);
        map.put(4, newCellStyle);
        map.put(6, newCellStyle);
        return map;
    }
}
