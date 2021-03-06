package com.ping.easyfile.core.handler;

import com.ping.easyfile.core.excel.WriteBuilderImpl;
import com.ping.easyfile.core.excel.IWriteBuilder;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelSheet;
import org.apache.commons.collections4.CollectionUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:20
 * @see
 */
public class ExportWriteHandler {
    private IWriteBuilder iExcelBuilder;

    public ExportWriteHandler(InputStream tempInputStream, OutputStream outputStream, ExcelTypeEnum excelTypeEnum) {
        this.iExcelBuilder = new WriteBuilderImpl(tempInputStream, outputStream, excelTypeEnum);
    }

    public void exportExcelV2007(List<ExcelSheet> excelSheets) {
        if (!CollectionUtils.isEmpty(excelSheets)) {
            for (ExcelSheet excelSheet : excelSheets) {
                iExcelBuilder.addContent(excelSheet);
            }
            iExcelBuilder.finish();
        }
    }

    public void exportExcelV2003(List<ExcelSheet> excelSheets) {
    }
}
