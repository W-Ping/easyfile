package com.ping.easyfile.core.excel;

import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelTable;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:14
 */
public interface IWriteBuilder {

    /**
     * 添加 excel sheet
     *
     * @param excelSheet
     */
    void addContent(ExcelSheet excelSheet);


    /**
     * 添加 excel table
     *
     * @param table
     */
    void addContent(ExcelTable table);

    /**
     * 合并行和列
     *
     * @param startRow
     * @param endRow
     * @param startCell
     * @param endCell
     */
    void merge(int startRow, int endRow, int startCell, int endCell);

    /**
     * 操作完成
     */
    void finish();
}
