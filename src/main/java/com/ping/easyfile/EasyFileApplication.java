package com.ping.easyfile;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.core.handler.ExcelReadHandler;
import com.ping.easyfile.core.handler.ExportWriteHandler;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelTable;
import com.ping.easyfile.exception.ExcelExportException;
import com.ping.easyfile.exception.ExcelParseException;
import com.ping.easyfile.request.ExcelWriteParam;
import com.ping.easyfile.response.ExcelWriteResponse;
import com.ping.easyfile.util.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/1 10:23
 */
public final class EasyFileApplication {
    private static Logger logger = LoggerFactory.getLogger(EasyFileApplication.class);

    /**
     * 导出Excel xlsx
     *
     * @param param
     * @return
     */
    public static ExcelWriteResponse exportV2007(ExcelWriteParam param) {
        logger.info("export excel startTime:" + System.currentTimeMillis());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ExcelWriteResponse excelWriteResponse = new ExcelWriteResponse();
        try {
            validateParam(param, ExcelTypeEnum.XLSX);
            inputStream = FileUtil.getResourcesFileInputStream(param.getExcelTemplateFile());
            outputStream = FileUtil.synGetResourcesFileOutputStream(param.getExcelOutFilePath(), param.getExcelFileName());
            ExportWriteHandler exportHandler = new ExportWriteHandler(inputStream, outputStream, ExcelTypeEnum.XLSX);
            List<ExcelSheet> excelSheets = param.getExcelSheets();
            exportHandler.exportExcelV2007(excelSheets);
            excelWriteResponse = new ExcelWriteResponse(FileConstant.SUCCESS_CODE, param.getExcelOutFileFullPath());
        } catch (Exception e) {
            logger.error("export excel is fail {}", e.getMessage());
            excelWriteResponse.setCode(FileConstant.FAIL_CODE);
            excelWriteResponse.setMessage(e.getMessage());
        } finally {
            FileUtil.close(inputStream, outputStream);
        }
        logger.info("export excel endTime:" + System.currentTimeMillis());
        return excelWriteResponse;
    }

    /**
     * 导出Excel xlsx(使用导出模板)
     *
     * @param param
     * @return
     */
    public static ExcelWriteResponse exportV2007WithTemp(ExcelWriteParam param) {
        if (StringUtils.isBlank(param.getExcelTemplateFile())) {
            return ExcelWriteResponse.fail("excel template is null");
        }
        return exportV2007(param);
    }

    /**
     * 读取 Excel
     *
     * @param excelPath       Excel 路径
     * @param excelReadTables Excel 表信息
     * @return
     */
    public static Map<Integer, List<Object>> readExcel(String excelPath, List<ExcelReadTable> excelReadTables) {
        logger.info("read excel startTime:" + System.currentTimeMillis());
        Map<Integer, List<Object>> result = null;
        InputStream inputStream = null;
        try {
            inputStream = FileUtil.getResourcesFileInputStream(excelPath);
            ExcelReadHandler excelReadHandler = new ExcelReadHandler(inputStream);
            result = excelReadHandler.read(excelReadTables);
        } catch (Exception e) {
            logger.error("read excel is fail {}", e.getMessage());
            throw new ExcelParseException(e);
        } finally {
            FileUtil.close(inputStream, null);
        }
        logger.info("read excel endTime:" + System.currentTimeMillis());
        return result;
    }

    /**
     * 验证参数
     *
     * @param excelWriteParam
     * @param excelTypeEnum
     * @throws ExportException
     */
    private static void validateParam(ExcelWriteParam excelWriteParam, ExcelTypeEnum excelTypeEnum) throws ExcelExportException {
        if (StringUtils.isBlank(excelWriteParam.getExcelOutFilePath())) {
            excelWriteParam.setExcelOutFilePath(FileUtil.createTempDirectory());
        }
        String excelFileName = excelWriteParam.getExcelFileName();
        if (StringUtils.isBlank(excelFileName)) {
            throw new ExcelExportException("excel fileName is null");
        } else {
            if (excelFileName.lastIndexOf(".") != -1) {
                if (ExcelTypeEnum.XLSX.equals(excelTypeEnum)
                        && !ExcelTypeEnum.XLSX.getValue().equals(excelFileName.substring(excelFileName.lastIndexOf(".")))) {
                    throw new ExcelExportException("excel fileType is not xlsx");
                } else if (ExcelTypeEnum.XLS.equals(excelTypeEnum)
                        && !ExcelTypeEnum.XLS.getValue().equals(excelFileName.substring(excelFileName.lastIndexOf(".")))) {
                    throw new ExcelExportException("excel fileType is not xls");
                }
            } else {
                throw new ExcelExportException("excel fileName is error");
            }
        }
        validateSheets(excelWriteParam.getExcelSheets());
    }

    private static void validateSheets(List<ExcelSheet> excelSheets) throws ExcelExportException {
        if (CollectionUtils.isEmpty(excelSheets)) {
            throw new ExcelExportException("excel sheet is null");
        }
        int sheetNo = -999;
        for (ExcelSheet sheet : excelSheets) {
            if (sheetNo == sheet.getSheetNo()) {
                throw new ExcelExportException("sheetNo is repetition");
            }
            sheetNo = sheet.getSheetNo();
            validateTables(sheet.getExcelTables());
        }
    }

    private static void validateTables(List<ExcelTable> tables) throws ExcelExportException {
        if (!CollectionUtils.isEmpty(tables)) {
            int tableNo = -999;
            for (ExcelTable table : tables) {
                if (tableNo == table.getTableNo()) {
                    throw new ExcelExportException("tableNo is repetition");
                }
                tableNo = table.getTableNo();
                if (CollectionUtils.isEmpty(table.getHead()) && null == table.getHeadClass()) {
                    throw new ExcelExportException("table head arguments is null");
                }
            }
        }
    }
}
