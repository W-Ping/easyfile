package com.ping.easyfile.core.handler;

import com.ping.easyfile.core.excel.IWriteBuilder;
import com.ping.easyfile.core.excel.WriteBuilderImpl;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.request.ExcelWriteParam;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:20
 */
public class ExportWriteHandler {
    private IWriteBuilder iExcelBuilder;

    public ExportWriteHandler(InputStream tempInputStream, OutputStream outputStream, ExcelTypeEnum excelTypeEnum, ExcelWriteParam excelWriteParam) throws IOException {
        this.iExcelBuilder = new WriteBuilderImpl(tempInputStream, outputStream, excelTypeEnum, excelWriteParam);
    }

    public void exportExcelV2007(List<ExcelSheet> excelSheets) {
        if (!CollectionUtils.isEmpty(excelSheets)) {
            iExcelBuilder.beforeWrite();
            for (ExcelSheet excelSheet : excelSheets) {
                iExcelBuilder.addContent(excelSheet);
            }
            iExcelBuilder.finish();
        }
    }
}
