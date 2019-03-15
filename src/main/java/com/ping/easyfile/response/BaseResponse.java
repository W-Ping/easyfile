package com.ping.easyfile.response;

import com.ping.easyfile.constant.FileConstant;

/**
 * @author liu_wp
 * @date Created in 2019/3/12 11:17
 * @see
 */
public class BaseResponse {
    private String code;
    private String message;

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse() {
        this.code = FileConstant.FAIL_CODE;
        this.message = "error";
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

}
