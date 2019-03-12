package com.ping.easyfile.response;

import com.ping.easyfile.constant.FileConstant;

/**
 * @author liu_wp
 * @date Created in 2019/3/12 11:16
 * @see
 */
public class ExportExcelResponse extends BaseResponse {

    private String excelOutFilePath;

    public ExportExcelResponse(String excelOutFilePath) {
        this.excelOutFilePath = excelOutFilePath;
    }

    public ExportExcelResponse(String code, String message, String excelOutFilePath) {
        super(code, message);
        this.excelOutFilePath = excelOutFilePath;
    }

    public ExportExcelResponse(String code, String excelOutFilePath) {
        this(code, null, excelOutFilePath);
    }

    public static ExportExcelResponse fail(String error) {
        ExportExcelResponse exportExcelResponse = new ExportExcelResponse();
        exportExcelResponse.setCode(FileConstant.FAIL_CODE);
        exportExcelResponse.setMessage(error);
        return exportExcelResponse;
    }

    public static ExportExcelResponse success(String excelOutFilePath) {
        ExportExcelResponse exportExcelResponse = new ExportExcelResponse();
        exportExcelResponse.setCode(FileConstant.SUCCESS_CODE);
        exportExcelResponse.setExcelOutFilePath(excelOutFilePath);
        return exportExcelResponse;
    }

    public ExportExcelResponse() {
        super();
    }

    public String getExcelOutFilePath() {
        return excelOutFilePath;
    }

    public void setExcelOutFilePath(String excelOutFilePath) {
        this.excelOutFilePath = excelOutFilePath;
    }
}
