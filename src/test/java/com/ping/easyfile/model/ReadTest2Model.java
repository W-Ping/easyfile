package com.ping.easyfile.model;

import com.ping.easyfile.annotation.ExcelColumn;
import com.ping.easyfile.excelmeta.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liu_wp
 * @date Created in 2019/3/25 10:42
 * @see
 */
public class ReadTest2Model extends BaseRowModel {
    @ExcelColumn(index = 0)
    private String a1;
    @ExcelColumn(index = 1)
    private int a2;
    @ExcelColumn(index = 2)
    private Double a3;
    @ExcelColumn(index = 3)
    private BigDecimal a4;
    @ExcelColumn(index = 4)
    private Date a5;
    @ExcelColumn(index = 5)
    private String a6;
    @ExcelColumn(index = 6)
    private String a7;
    @ExcelColumn(index = 7)
    private Integer a8;
    @ExcelColumn(index = 8)
    private String a9;

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public Double getA3() {
        return a3;
    }

    public void setA3(Double a3) {
        this.a3 = a3;
    }

    public BigDecimal getA4() {
        return a4;
    }

    public void setA4(BigDecimal a4) {
        this.a4 = a4;
    }

    public Date getA5() {
        return a5;
    }

    public void setA5(Date a5) {
        this.a5 = a5;
    }

    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    public Integer getA8() {
        return a8;
    }

    public void setA8(Integer a8) {
        this.a8 = a8;
    }

    public String getA9() {
        return a9;
    }

    public void setA9(String a9) {
        this.a9 = a9;
    }
}
