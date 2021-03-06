package com.ping.easyfile.model;

import com.ping.easyfile.annotation.ExcelWriteProperty;
import com.ping.easyfile.excelmeta.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

public class ExcelTest2Model extends BaseRowModel {
    @ExcelWriteProperty(value = {"表头1", "表头1", "表头12"}, index = 0)
    protected String p1;

    @ExcelWriteProperty(value = {"表头1", "表头1", "表头13"}, index = 1)
    protected String p2;

    @ExcelWriteProperty(value = {"表头2", "表头2", "表头2"}, index = 2)
    private int p3;

    @ExcelWriteProperty(value = {"表头3", "表头3", "表头31"}, index = 3)
    private long p4;

    @ExcelWriteProperty(value = {"表头5", "表头5", "表头51"}, index = 4)
    private String p5;

    @ExcelWriteProperty(value = {"表头5", "表头5", "表头52"}, index = 5)
    private float p6;

    @ExcelWriteProperty(value = {"表头6", "表头61", "表头612"}, index = 6)
    private BigDecimal p7;

    @ExcelWriteProperty(value = {"表头6", "表头62", "表头621"}, index = 7)
    private Date p8;

    @ExcelWriteProperty(value = {"表头6", "表头62", "表头622"}, index = 9)
    private double p9;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public int getP3() {
        return p3;
    }

    public void setP3(int p3) {
        this.p3 = p3;
    }

    public long getP4() {
        return p4;
    }

    public void setP4(long p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public float getP6() {
        return p6;
    }

    public void setP6(float p6) {
        this.p6 = p6;
    }

    public BigDecimal getP7() {
        return p7;
    }

    public void setP7(BigDecimal p7) {
        this.p7 = p7;
    }

    public Date getP8() {
        return p8;
    }

    public void setP8(Date p8) {
        this.p8 = p8;
    }

    public double getP9() {
        return p9;
    }

    public void setP9(double p9) {
        this.p9 = p9;
    }
}
