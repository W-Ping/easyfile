package com.ping.easyfile.core.excel;

import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelTable;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:14
 * @see
 */
public interface IExcelBuilder {

    /**
     * @param excelSheet
     */
    void addContent(ExcelSheet excelSheet);


    /**
     * @param table
     */
    void addContent(ExcelTable table);

    /**
     *
     */
    void finish();
}
