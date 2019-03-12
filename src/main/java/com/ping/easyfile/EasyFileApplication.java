package com.ping.easyfile;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.core.handler.ExportHandler;
import com.ping.easyfile.em.ExcelTypeEnum;
import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.excelmeta.ExcelTable;
import com.ping.easyfile.request.ExportExcelParam;
import com.ping.easyfile.response.BaseResponse;
import com.ping.easyfile.response.ExportExcelResponse;
import com.ping.easyfile.util.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.ExportException;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/1 10:23
 * @see
 */
public class EasyFileApplication {
    private static Logger logger = LoggerFactory.getLogger(EasyFileApplication.class);

    /**
     * @param paramVo
     * @return
     */
    public static ExportExcelResponse exportV2007(ExportExcelParam param) {
        logger.info("export excel startTime:" + System.currentTimeMillis());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ExportExcelResponse exportExcelResponse = new ExportExcelResponse();
        try {
            validateParam(param, ExcelTypeEnum.XLSX);
            inputStream = FileUtil.getResourcesFileInputStream(param.getExcelTemplateFile());
            outputStream = FileUtil.synGetResourcesFileOutputStream(param.getExcelOutFilePath(), param.getExcelFileName());
            ExportHandler exportHandler = new ExportHandler(inputStream, outputStream, ExcelTypeEnum.XLSX);
            List<ExcelSheet> excelSheets = param.getExcelSheets();
            exportHandler.exportExcelV2007(excelSheets);
            exportExcelResponse = new ExportExcelResponse(FileConstant.SUCCESS_CODE, FileConstant.SUCCESS_CODE, param.getExcelOutFileFullPath());
        } catch (Exception e) {
            e.printStackTrace();
            exportExcelResponse.setMessage(e.getMessage());
        } finally {
            FileUtil.close(inputStream, outputStream);
        }
        logger.info("export excel endTime:" + System.currentTimeMillis());
        return exportExcelResponse;
    }

    public static ExportExcelResponse exportV2007WithTemp(ExportExcelParam param) {
        if (StringUtils.isBlank(param.getExcelTemplateFile())) {
            return ExportExcelResponse.fail("excel template is null");
        }
        return exportV2007(param);
    }


    /**
     * @param exportExcelParam
     * @param excelTypeEnum
     * @throws ExportException
     */
    private static void validateParam(ExportExcelParam exportExcelParam, ExcelTypeEnum excelTypeEnum) throws ExportException {
        if (StringUtils.isBlank(exportExcelParam.getExcelOutFilePath())) {
            exportExcelParam.setExcelOutFilePath(FileUtil.createTempDirectory());
        }
        String excelFileName = exportExcelParam.getExcelFileName();
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
        validateSheets(exportExcelParam.getExcelSheets());
    }

    private static void validateSheets(List<ExcelSheet> excelSheets) throws ExportException {
        if (CollectionUtils.isEmpty(excelSheets)) {
            throw new ExportException("excel sheet is null");
        }
        int sheetNo = -1;
        for (ExcelSheet sheet : excelSheets) {
            if (sheetNo == sheet.getSheetNo()) {
                throw new ExportException("multi sheetNo is identical");
            }
            sheetNo = sheet.getSheetNo();
            validateTables(sheet.getExcelTables());
        }
    }

    private static void validateTables(List<ExcelTable> tables) throws ExportException {
        if (!CollectionUtils.isEmpty(tables)) {
            int tableNo = -1;
            for (ExcelTable table : tables) {
                if (tableNo == table.getTableNo()) {
                    throw new ExportException("multi tableNo is identical");
                }
                tableNo = table.getTableNo();
                if (CollectionUtils.isEmpty(table.getHead()) && null == table.getHeadClass()) {
                    throw new ExportException("table head arguments is null");
                }
            }
        }
    }
}