package com.ping.easyfile.excelmeta;

import com.ping.easyfile.constant.FileConstant;

/**
 * @author liu_wp
 * @date Created in 2019/3/1 10:25
 * @see
 */
public class ExcelResponseResult {
    private String code;
    private String message;
    private Object resultObj;

    /**
     * @param error
     * @return
     */
    public static ExcelResponseResult fail(String error) {
        ExcelResponseResult responseResult = new ExcelResponseResult();
        responseResult.setMessage(error);
        responseResult.setCode(FileConstant.FAIL_CODE);
        return responseResult;
    }

    public static ExcelResponseResult success(String message, Object resultObj) {
        ExcelResponseResult responseResult = new ExcelResponseResult();
        responseResult.setResultObj(resultObj);
        responseResult.setMessage(message);
        responseResult.setCode(FileConstant.SUCCESS_CODE);
        return responseResult;
    }
    public static ExcelResponseResult success(Object resultObj) {
        ExcelResponseResult responseResult = new ExcelResponseResult();
        responseResult.setResultObj(resultObj);
        responseResult.setCode(FileConstant.SUCCESS_CODE);
        return responseResult;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }
}
