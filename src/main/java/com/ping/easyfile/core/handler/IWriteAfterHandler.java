package com.ping.easyfile.core.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 导出后置处理接口
 *
 * @author liu_wp
 * @date Created in 2019/3/29 14:57
 */
public interface IWriteAfterHandler {

    /**
     * Excel 行
     *
     * @param rowNum
     * @param row
     */
    void row(int rowNum, Row row);

    /**
     * Excel 单元格
     *
     * @param cellNum
     * @param cell
     */
    void cell(int cellNum, Cell cell);
}
