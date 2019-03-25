package com.ping.easyfile;

import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.model.ReadTestModel;
import com.ping.easyfile.util.JSONUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/25 10:45
 * @see
 */
public class ReadTest {
    private static Logger logger = LoggerFactory.getLogger(ReadTest.class);

    @Test
    public void readExcelTest() {
        String excelPath = "red_test.xlsx";
        List<ExcelReadTable> excelReadTables = new ArrayList<>();
        ExcelReadTable excelReadTable = new ExcelReadTable(1, 0, 5);
        excelReadTable.setDataModelClass(ReadTestModel.class);
        excelReadTables.add(excelReadTable);
        Map<Integer, List<Object>> integerListMap = EasyFileApplication.readExcel(excelPath, excelReadTables);
        List<Object> list = integerListMap.get(1);
        for (Object obj : list) {
            ReadTestModel d = (ReadTestModel) obj;
            System.out.println(d.getDate().getHours());
//            System.out.println(DateUtil.formatDate(d.getDate()));
        }
        logger.info("解析结果{}", JSONUtil.objectToString(integerListMap));
    }
}
