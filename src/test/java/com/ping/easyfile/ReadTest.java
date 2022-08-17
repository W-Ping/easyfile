package com.ping.easyfile;

import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.model.ReadTest2Model;
import com.ping.easyfile.model.ReadTestModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/25 10:45
 * @see
 */
public class ReadTest {

    @Test
    public void readExcelTest() {
        String excelPath = "red_test.xlsx";
        List<ExcelReadTable> excelReadTables = new ArrayList<>();
        ExcelReadTable excelReadTable = new ExcelReadTable(1);
        excelReadTable.setDataModelClass(ReadTestModel.class);
        excelReadTables.add(excelReadTable);
        ExcelReadTable excelReadTable1 = new ExcelReadTable(2);
        excelReadTable1.setDataModelClass(ReadTest2Model.class);
        excelReadTables.add(excelReadTable1);
        Map<Integer, List<Object>> integerListMap = EasyFileApplication.readExcel(excelPath, excelReadTables);
        List<Object> list1 = integerListMap.get(1);
        List<Object> list2 = integerListMap.get(2);
        Assert.assertTrue(!list1.isEmpty());
        Assert.assertTrue(!list2.isEmpty());
    }
}
