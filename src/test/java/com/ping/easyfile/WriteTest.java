package com.ping.easyfile;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.data.TestData;
import com.ping.easyfile.em.TableLayoutEnum;
import com.ping.easyfile.excelmeta.ExcelFont;
import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelStyle;
import com.ping.easyfile.excelmeta.ExcelTable;
import com.ping.easyfile.handler.WriteAfterHandlerImpl;
import com.ping.easyfile.handler.WriteBeforeHandler2Impl;
import com.ping.easyfile.handler.WriteBeforeHandlerImpl;
import com.ping.easyfile.model.ExcelTest1Model;
import com.ping.easyfile.model.ExcelTest2Model;
import com.ping.easyfile.model.ExportTest3Model;
import com.ping.easyfile.model.ExportTestModel;
import com.ping.easyfile.request.ExcelWriteParam;
import com.ping.easyfile.request.WaterMark;
import com.ping.easyfile.response.ExcelWriteResponse;
import com.ping.easyfile.util.FileUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author liu_wp
 * @date Created in 2019/3/7 15:59
 * @see
 */
public class WriteTest {
    @Test
    public void exportWithTmpWaterMark() {
        String outFilePath = "/opt/excel_watermark";
        String outFileName = TestData.createUniqueFileName("测试默认导出1") + ".xlsx";
        ExcelTable t1 = new ExcelTable(1, null, ExcelTest1Model.class, TestData.createTestListJavaModel1(), false);
        t1.setFirstRowIndex(1);
        ExcelSheet sheet1 = new ExcelSheet(0, "测试默认1", Arrays.asList(t1));
        final ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(Arrays.asList(sheet1))
                .waterMark(new WaterMark("测试水印\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))))
                .build();
        String tmpFilePath = "write_test.xlsx";
        final InputStream tmpInputStream = FileUtil.getResourcesFileInputStream(tmpFilePath);
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam, tmpInputStream);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }


    @Test
    public void exportWithTmp1() {
        String outFilePath = "/opt/excel_tmp";
        String outFileName = TestData.createUniqueFileName("测试默认导出1") + ".xlsx";
        ExcelTable t1 = new ExcelTable(1, null, ExcelTest1Model.class, TestData.createTestListJavaModel1(), false);
        t1.setFirstRowIndex(1);
        ExcelSheet sheet1 = new ExcelSheet(0, "测试默认1", Arrays.asList(t1));
        final ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(Arrays.asList(sheet1))
                .build();
        String tmpFilePath = "write_test.xlsx";
        final InputStream tmpInputStream = FileUtil.getResourcesFileInputStream(tmpFilePath);
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam, tmpInputStream);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportWithTmp2() {
        String outFilePath = "/opt/excel_tmp";
        String outFileName = TestData.createUniqueFileName("测试默认导出1") + ".xlsx";
        String tmpFilePath = "write_test.xlsx";
        ExcelTable t1 = new ExcelTable(1, null, ExcelTest1Model.class, TestData.createTestListJavaModel1(), false);
        t1.setFirstRowIndex(1);
        ExcelSheet sheet1 = new ExcelSheet(0, "测试默认1", Arrays.asList(t1));
        final ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelTemplateFile(tmpFilePath)
                .excelOutFilePath(outFilePath)
                .excelSheets(Arrays.asList(sheet1))
                .build();
        ExcelWriteResponse export = EasyFileApplication.exportV2007WithTemp(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportDefault3() {
        String outFilePath = "/opt/excel_default";
        String outFileName = TestData.createUniqueFileName("测试默认导出") + ".xlsx";
        ExcelTable t1 = new ExcelTable(1, null, ExportTestModel.class, TestData.createTestListJavaMode2());
        ExcelSheet sheet1 = new ExcelSheet(0, "测试默认", Arrays.asList(t1));
        final ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(Arrays.asList(sheet1))
                .build();
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportV2007WithLayout() {
        String outFilePath = "/opt/excel_layout";
        String outFileName = TestData.createUniqueFileName("export") + ".xlsx";
        ExcelTable excelTable1 = new ExcelTable(1, null, ExportTestModel.class, TestData.createTestListJavaMode());
        ExcelTable excelTable2 = new ExcelTable(2, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        ExcelStyle excelStyleHead = new ExcelStyle();
        excelStyleHead.setExcelBackGroundColor(IndexedColors.RED);
        ExcelFont excelFontHead = new ExcelFont();
        excelFontHead.setFontHeightInPoints((short) 25);
        excelFontHead.setBold(true);
        excelFontHead.setFontColor(IndexedColors.BLUE);
        excelStyleHead.setExcelFont(excelFontHead);
        excelTable2.setTableHeadStyle(excelStyleHead);
        ExcelStyle excelStyle = new ExcelStyle();
        excelStyle.setExcelBackGroundColor(IndexedColors.BLUE);
        ExcelFont excelFont = new ExcelFont();
        excelFont.setFontHeightInPoints((short) 11);
        excelFont.setBold(true);
        excelFont.setFontColor(IndexedColors.WHITE);
        excelStyle.setExcelFont(excelFont);
        excelTable2.setTableStyle(excelStyle);
        excelTable2.setTableLayoutEnum(TableLayoutEnum.RIGHT);
        ExcelTable excelTable3 = new ExcelTable(3, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        ExcelTable excelTable4 = new ExcelTable(4, null, ExportTestModel.class, TestData.createTestListJavaMode());
        ExcelStyle excelStyleHead4 = new ExcelStyle();
        excelStyleHead4.setExcelBackGroundColor(IndexedColors.RED);
        ExcelFont excelFontHead4 = new ExcelFont();
        excelFontHead4.setFontHeightInPoints((short) 25);
        excelFontHead4.setBold(true);
        excelStyleHead4.setExcelFont(excelFontHead4);
        excelTable4.setTableHeadStyle(excelStyleHead4);
        ExcelStyle excelStyle4 = new ExcelStyle();
        excelStyle4.setExcelBackGroundColor(IndexedColors.BLACK);
        ExcelFont excelFont4 = new ExcelFont();
        excelFont4.setFontHeightInPoints((short) 13);
        excelFont4.setBold(true);
        excelFont4.setFontColor(IndexedColors.RED);
        excelStyle4.setExcelFont(excelFont4);
        excelTable4.setTableStyle(excelStyle4);
        excelTable4.setTableLayoutEnum(TableLayoutEnum.RIGHT);
        ExcelTable excelTable5 = new ExcelTable(5, null, ExportTestModel.class, TestData.createTestListJavaMode());
        excelTable5.setTableLayoutEnum(TableLayoutEnum.RIGHT);
        excelTable5.setSpaceNum(2);
        ExcelTable excelTable6 = new ExcelTable(6, null, ExportTest3Model.class, TestData.createTestListJavaMode3());
        List<ExcelTable> excelTables = new ArrayList<>();
        excelTables.add(excelTable1);
        excelTables.add(excelTable2);
        excelTables.add(excelTable3);
        excelTables.add(excelTable4);
        excelTables.add(excelTable5);
        excelTables.add(excelTable6);
        ExcelSheet sheet1 = new ExcelSheet(0, "测试Layout1", excelTables);
        Map columnWidthMap = new HashMap();
        columnWidthMap.put(4, 30);
        columnWidthMap.put(6, 30);
        sheet1.setColumnWidthMap(columnWidthMap);
        ExcelSheet sheet2 = new ExcelSheet(1, "测试Layout2", excelTables);
        List<ExcelSheet> sheets = new ArrayList<ExcelSheet>() {{
            add(sheet1);
            add(sheet2);
        }};
        ExcelWriteParam excelWriteParam = new ExcelWriteParam();
        excelWriteParam.setExcelFileName(outFileName);
        excelWriteParam.setExcelOutFilePath(outFilePath);
        excelWriteParam.setExcelSheets(sheets);
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportV2007WithCellStyle() {
        String outFilePath = "/opt/excel_style";
        String outFileName = TestData.createUniqueFileName("export") + ".xlsx";
        ExcelTable excelTable1 = new ExcelTable(1, null, ExportTestModel.class, TestData.createTestListJavaModeStyle());
        ExcelTable excelTable2 = new ExcelTable(2, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        List<ExcelTable> excelTables = new ArrayList<ExcelTable>() {{
            add(excelTable1);
            add(excelTable2);
        }};
        ExcelSheet sheet1 = new ExcelSheet(0, "测试style", excelTables);
        List<ExcelSheet> sheets = new ArrayList<ExcelSheet>() {{
            add(sheet1);
        }};
        ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(sheets)
                .build();
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportV2007WithNoHead() {
        String outFilePath = "/opt/excel_head";
        String outFileName = TestData.createUniqueFileName("export") + ".xlsx";
        ExcelTable excelTable1 = new ExcelTable(1, null, ExportTestModel.class, TestData.createTestListJavaModeStyle());
        excelTable1.setNeedHead(true);
        excelTable1.setFirstRowIndex(1);
        ExcelTable excelTable2 = new ExcelTable(2, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        ExcelTable excelTable3 = new ExcelTable(3, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        excelTable3.setTableLayoutEnum(TableLayoutEnum.RIGHT);
        excelTable2.setNeedHead(false);
        excelTable2.setSpaceNum(0);
        excelTable3.setSpaceNum(2);
        excelTable3.setNeedHead(false);
        List<ExcelTable> excelTables = new ArrayList<ExcelTable>() {{
            add(excelTable1);
            add(excelTable2);
            add(excelTable3);
        }};
        ExcelSheet sheet1 = new ExcelSheet(0, "测试head", excelTables);
        ExcelSheet sheet2 = new ExcelSheet(1, "测试head2", excelTables);
        Integer[] cell = {8, 8, 5, 6};
        Integer[] cell3 = {9, 9, 5, 7};
        List<Integer[]> ss = new ArrayList<>();
        ss.add(cell);
        ss.add(cell3);
        sheet1.setMergeData(ss);
        List<ExcelSheet> sheets = new ArrayList<ExcelSheet>() {{
            add(sheet1);
            add(sheet2);
        }};
        ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(sheets)
                .build();
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }

    @Test
    public void exportV2007WithHandler() {
        String outFilePath = "/opt/excel_handler";
        String outFileName = TestData.createUniqueFileName("export") + ".xlsx";
        ExcelTable excelTable1 = new ExcelTable(1, null, ExportTestModel.class, TestData.createTestListJavaModeStyle());
        excelTable1.setiWriteAfterHandler(new WriteAfterHandlerImpl());
        excelTable1.setiWriteBeforeHandler(new WriteBeforeHandlerImpl());
        ExcelTable excelTable2 = new ExcelTable(2, null, ExcelTest2Model.class, TestData.createTestListJavaMode2());
        excelTable2.setiWriteBeforeHandler(new WriteBeforeHandler2Impl());
        List<ExcelTable> excelTables = new ArrayList<ExcelTable>() {{
            add(excelTable1);
            add(excelTable2);
        }};
        ExcelSheet sheet1 = new ExcelSheet(0, "测试style", excelTables);
        List<ExcelSheet> sheets = new ArrayList<ExcelSheet>() {{
            add(sheet1);
        }};
        ExcelWriteParam excelWriteParam = ExcelWriteParam.builder()
                .excelFileName(outFileName)
                .excelOutFilePath(outFilePath)
                .excelSheets(sheets)
                .build();
        ExcelWriteResponse export = EasyFileApplication.exportV2007(excelWriteParam);
        Assert.assertEquals(export.getCode(), FileConstant.SUCCESS_CODE);
    }
}
