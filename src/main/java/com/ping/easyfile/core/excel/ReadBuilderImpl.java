package com.ping.easyfile.core.excel;

import com.ping.easyfile.context.ReadContext;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelReadTable;
import org.apache.commons.collections4.CollectionUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 14:48
 * @see
 */
public class ReadBuilderImpl implements IReadBuilder {
    private ReadContext readContext;

    public ReadBuilderImpl(InputStream inputStream) {
        try {
            this.readContext = new ReadContext(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<Integer, List<Object>> read(List<ExcelReadTable> excelReadTables) {
        Map<Integer, List<Object>> readTableMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(excelReadTables)) {
            for (ExcelReadTable excelReadTable : excelReadTables) {
                List<Object> list = readContext.readExcelTable(excelReadTable);
                readTableMap.put(excelReadTable.getTableNo(), list);
            }
        }
        return readTableMap;
    }
}
