package com.ping.easyfile.em;

import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liu_wp
 * @date Created in 2019/3/1 10:19
 * @see
 */
public enum ExcelTypeEnum {

    XLS(".xls"), XLSX(".xlsx");

    private String value;

    ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ExcelTypeEnum valueOf(InputStream inputStream){
        try {
            if (!inputStream.markSupported()) {
                return null;
            }
            FileMagic fileMagic =  FileMagic.valueOf(inputStream);
            if(FileMagic.OLE2.equals(fileMagic)){
                return XLS;
            }
            if(FileMagic.OOXML.equals(fileMagic)){
                return XLSX;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
