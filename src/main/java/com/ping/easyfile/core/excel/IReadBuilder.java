package com.ping.easyfile.core.excel;

import com.ping.easyfile.excelmeta.ExcelReadTable;

import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 12:04
 */
public interface IReadBuilder {
    /**
     * 读取 Excel
     *
     * @param excelReadTables
     * @return
     */
    Map<Integer, List<Object>> read(List<ExcelReadTable> excelReadTables);
}
