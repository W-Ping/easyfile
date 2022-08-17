package com.ping.easyfile.model;

import com.ping.easyfile.annotation.ExcelWriteProperty;
import com.ping.easyfile.excelmeta.BaseRowModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lwp
 * @date 2022-08-16
 */
public class ExcelTest1Model extends BaseRowModel {
    @ExcelWriteProperty(index = 0)
    protected String p1;

    @ExcelWriteProperty(index = 1)
    protected String p2;

    @ExcelWriteProperty(index = 2)
    private Integer p3;

    @ExcelWriteProperty(index = 3)
    private Long p4;

    @ExcelWriteProperty(index = 4)
    private String p5;

    @ExcelWriteProperty(index = 5)
    private Float p6;

    @ExcelWriteProperty(index = 6)
    private BigDecimal p7;

    @ExcelWriteProperty(index = 7)
    private Date p8;

    @ExcelWriteProperty(index = 9)
    private Double p9;

    @ExcelWriteProperty(index = 10,format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime p10;

    @ExcelWriteProperty(index = 11,format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Timestamp p11;


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

    public Integer getP3() {
        return p3;
    }

    public void setP3(Integer p3) {
        this.p3 = p3;
    }

    public Long getP4() {
        return p4;
    }

    public void setP4(Long p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public Float getP6() {
        return p6;
    }

    public void setP6(Float p6) {
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

    public Double getP9() {
        return p9;
    }

    public void setP9(Double p9) {
        this.p9 = p9;
    }

    public LocalDateTime getP10() {
        return p10;
    }

    public void setP10(LocalDateTime p10) {
        this.p10 = p10;
    }

    public Timestamp getP11() {
        return p11;
    }

    public void setP11(Timestamp p11) {
        this.p11 = p11;
    }
}
