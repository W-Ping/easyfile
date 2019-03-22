package com.ping.easyfile.core.excel;

import com.ping.easyfile.context.ReadContext;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelReadTable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 14:48
 * @see
 */
public class IReadBuilderImpl implements IReadBuilder {
    private ReadContext readContext;

    public IReadBuilderImpl(InputStream tempInputStream) {
        try {
            this.readContext = new ReadContext(tempInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<Integer, List<Object>> read(List<ExcelReadTable> excelReadTables) {
        return null;
    }
}
