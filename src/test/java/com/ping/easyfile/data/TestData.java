package com.ping.easyfile.data;

import com.ping.easyfile.excelmeta.ExcelStyle;
import com.ping.easyfile.model.ExcelTest1Model;
import com.ping.easyfile.model.ExcelTest2Model;
import com.ping.easyfile.model.ExportTest3Model;
import com.ping.easyfile.model.ExportTestModel;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/7 16:20
 * @see
 */
public class TestData {
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

    public static String createUniqueFileName(String fileName) {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(TestData.format);
        if (StringUtils.isNotBlank(fileName)) {
            format = format.concat("_").concat(fileName);
        }
        return format;
    }

    public static ExcelStyle tableExcelStyle() {
        ExcelStyle excelStyle = new ExcelStyle();
//        excelStyle.setCurrentCellStyle();
        return excelStyle;
    }

    public static List<List<String>> createHeadList() {
        List<List<String>> head = new ArrayList<>();
        List<String> head1 = new ArrayList<>();
        head1.add("表1");
        head1.add("表1");
        head1.add("表21");
        List<String> head2 = new ArrayList<>();
        head2.add("表2");
        head2.add("表21");
        List<String> head3 = new ArrayList<>();
        head3.add("表3");
        head3.add("表3");
        head3.add("表3");
        List<String> head4 = new ArrayList<>();
        head4.add("表4");
        head4.add("表4");
        head4.add("表4");
        head.add(head1);
        head.add(head2);
        head.add(head3);
        head.add(head4);
        return head;
    }

    public static List<ExportTestModel> createTestListJavaMode() {
        List<ExportTestModel> model1s = new ArrayList<ExportTestModel>();
        for (int i = 0; i < 5; i++) {
            ExportTestModel model1 = new ExportTestModel();
            model1.setP1("第一列，第" + (i + 1) + "行");
            model1.setP2("32323JJfdf");
            model1.setP3(33 + i);
            model1.setP4(44);
            model1.setP5("55ces");
            model1.setP6(666.2f);
            model1.setP7(new BigDecimal("23.13991399"));
            model1.setP8(new Date());
            model1.setP9("PPPP9999");
            model1.setP10(1111.77 + i);
            model1s.add(model1);
        }
        return model1s;
    }

    public static List<ExportTestModel> createTestListJavaModeStyle() {
        List<ExportTestModel> model1s = new ArrayList<ExportTestModel>();
        for (int i = 0; i < 5; i++) {
            ExportTestModel model1 = new ExportTestModel();
            model1.setP1("第一列，第" + (i + 1) + "行");
            model1.setP2("32323JJfdf");
            model1.setP3(33 + i);
            model1.setP4(44);
            model1.setP5("55ces");
            model1.setP6(666.2f);
            model1.setP7(new BigDecimal("23.13991399"));
            model1.setP8(new Date());
            model1.setP9("PPPP9999");
            model1.setP10(1111.77 + i);
//            model1.setCellStyleMap(cellStyleMap);
            model1s.add(model1);
        }
        return model1s;
    }

    public static List<ExcelTest2Model> createTestListJavaMode2() {
        List<ExcelTest2Model> model1s = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelTest2Model model1 = new ExcelTest2Model();
            model1.setP1("第一列，第" + (i + 1) + "行");
            model1.setP2("32323JJfdf");
            model1.setP3(22 + i);
            model1.setP4(88);
            model1.setP5("66ces");
            model1.setP6(888.2f);
            model1.setP7(new BigDecimal("45.13991399"));
            model1.setP8(new Date());
            model1.setP9(331.77 + i);
            model1s.add(model1);
        }
        return model1s;
    }

    public static List<ExportTest3Model> createTestListJavaMode3() {
        List<ExportTest3Model> model1s = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ExportTest3Model model1 = new ExportTest3Model();
            model1.setP1("第一列，第" + (i + 1) + "行");
            model1.setP2("39087623JJfdf");
            model1.setP3(42 + i);
            model1.setP4(912);
            model1.setP5("66ces");
            model1.setP6(333.2f);
            model1.setP7(new BigDecimal("45.13991399"));
            model1s.add(model1);
        }
        return model1s;
    }

    public static List<ExcelTest1Model> createTestListJavaModel1() {
        List<ExcelTest1Model> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            ExcelTest1Model model1 = new ExcelTest1Model();
            model1.setP1("第一列，第" + (i + 1) + "行");
            model1.setP2("39087623JJfdf");
            model1.setP3(42 + i);
            model1.setP4(893434L + i);
            model1.setP5("66ces");
            model1.setP6(333.2f);
            model1.setP7(new BigDecimal("45.13991399"));
            model1.setP8(new Date());
            model1.setP9(Double.parseDouble("19.34"));
            model1.setP10(LocalDateTime.now());
            model1.setP11(new Timestamp(System.currentTimeMillis()));
            list.add(model1);
        }
        return list;
    }
}
