package com.ping.easyfile.core.handler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

/**
 * 导出前置处理接口
 *
 * @author liu_wp
 * @date Created in 2019/3/29 14:57
 */
public interface IWriteBeforeHandler {
    /**
     * 设置table 列样式
     *
     * @param workbook
     * @return
     */
    Map<Integer, CellStyle> initCellStyle(Workbook workbook);
}
