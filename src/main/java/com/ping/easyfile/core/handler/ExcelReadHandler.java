package com.ping.easyfile.core.handler;

import com.ping.easyfile.core.excel.IReadBuilder;
import com.ping.easyfile.core.excel.ReadBuilderImpl;
import com.ping.easyfile.excelmeta.ExcelReadTable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 14:47
 * @see
 */
public class ExcelReadHandler {
    private IReadBuilder iReadBuilder;

    public ExcelReadHandler(InputStream inputStream) {
        this.iReadBuilder = new ReadBuilderImpl(inputStream);
    }

    public Map<Integer, List<Object>> read(List<ExcelReadTable> excelReadTables) {
        return iReadBuilder.read(excelReadTables);
    }
}
