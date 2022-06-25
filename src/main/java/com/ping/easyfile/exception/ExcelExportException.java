package com.ping.easyfile.exception;

/**
 * @author lwp
 * @date 2022-06-25
 */
public class ExcelExportException extends RuntimeException{
    public ExcelExportException() {
        super();
    }

    public ExcelExportException(String message) {
        super(message);
    }

    public ExcelExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelExportException(Throwable cause) {
        super(cause);
    }
}
