package com.ping.easyfile.model;

import com.ping.easyfile.annotation.ExcelReadProperty;
import com.ping.easyfile.excelmeta.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liu_wp
 * @date Created in 2019/3/25 10:42
 * @see
 */
public class ReadTestModel extends BaseRowModel {
    @ExcelReadProperty(index = 0)
    private String a;
    @ExcelReadProperty(index = 1)
    private BigDecimal d;
    @ExcelReadProperty(index = 2)
    private int c;
    @ExcelReadProperty(index = 3)
    private Double b;
    @ExcelReadProperty(index = 4)
    private Integer e;
    @ExcelReadProperty(index = 5, format = "HH:mm:ss")
    private Date date;
    @ExcelReadProperty(index = 6)
    private String phone;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public BigDecimal getD() {
        return d;
    }

    public void setD(BigDecimal d) {
        this.d = d;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
