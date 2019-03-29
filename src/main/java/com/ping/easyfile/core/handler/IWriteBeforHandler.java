package com.ping.easyfile.core.handler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/29 14:57
 * @see
 */
public interface IWriteBeforHandler {
    /**
     * 设置table 列样式
     *
     * @return
     */
    Map<Integer, CellStyle> initCellStyle(Workbook workbook);
}
