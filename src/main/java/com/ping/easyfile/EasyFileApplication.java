package com.ping.easyfile;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.core.handler.ExcelReadHandler;
import com.ping.easyfile.core.handler.ExportWriteHandler;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelReadTable;
import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelTable;
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
 * @see
 */
public class EasyFileApplication {
    private static Logger logger = LoggerFactory.getLogger(EasyFileApplication.class);

    /**
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
            e.printStackTrace();
            excelWriteResponse.setMessage(e.getMessage());
        } finally {
            FileUtil.close(inputStream, outputStream);
        }
        logger.info("export excel endTime:" + System.currentTimeMillis());
        return excelWriteResponse;
    }

    /**
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
     * @param excelReadTables
     * @param excelPath
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
            e.printStackTrace();
        } finally {
            FileUtil.close(inputStream, null);
        }
        logger.info("read excel endTime:" + System.currentTimeMillis());
        return result;
    }

    /**
     * @param excelWriteParam
     * @param excelTypeEnum
     * @throws ExportException
     */
    private static void validateParam(ExcelWriteParam excelWriteParam, ExcelTypeEnum excelTypeEnum) throws ExportException {
        if (StringUtils.isBlank(excelWriteParam.getExcelOutFilePath())) {
            excelWriteParam.setExcelOutFilePath(FileUtil.createTempDirectory());
        }
        String excelFileName = excelWriteParam.getExcelFileName();
        if (StringUtils.isBlank(excelFileName)) {
            throw new ExportException("excel fileName is null");
        } else {
            if (excelFileName.lastIndexOf(".") != -1) {
                if (ExcelTypeEnum.XLSX.equals(excelTypeEnum)
                        && !ExcelTypeEnum.XLSX.getValue().equals(excelFileName.substring(excelFileName.lastIndexOf(".")))) {
                    throw new ExportException("excel fileType is not xlsx");
                } else if (ExcelTypeEnum.XLS.equals(excelTypeEnum)
                        && !ExcelTypeEnum.XLS.getValue().equals(excelFileName.substring(excelFileName.lastIndexOf(".")))) {
                    throw new ExportException("excel fileType is not xls");
                }
            } else {
                throw new ExportException("excel fileName is error");
            }
        }
        validateSheets(excelWriteParam.getExcelSheets());
    }

    private static void validateSheets(List<ExcelSheet> excelSheets) throws ExportException {
        if (CollectionUtils.isEmpty(excelSheets)) {
            throw new ExportException("excel sheet is null");
        }
        int sheetNo = -999;
        for (ExcelSheet sheet : excelSheets) {
            if (sheetNo == sheet.getSheetNo()) {
                throw new ExportException("sheetNo is repetition");
            }
            sheetNo = sheet.getSheetNo();
            validateTables(sheet.getExcelTables());
        }
    }

    private static void validateTables(List<ExcelTable> tables) throws ExportException {
        if (!CollectionUtils.isEmpty(tables)) {
            int tableNo = -999;
            for (ExcelTable table : tables) {
                if (tableNo == table.getTableNo()) {
                    throw new ExportException("tableNo is repetition");
                }
                tableNo = table.getTableNo();
                if (CollectionUtils.isEmpty(table.getHead()) && null == table.getHeadClass()) {
                    throw new ExportException("table head arguments is null");
                }
            }
        }
    }
}
