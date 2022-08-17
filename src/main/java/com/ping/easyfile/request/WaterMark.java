package com.ping.easyfile.request;

/**
 * @author lwp
 * @date 2022-08-17
 */
public class WaterMark {
    private String waterMarkText;

    private int width;
    private int height;

    private int size;

    public WaterMark(String waterMarkText) {
        this(waterMarkText, 500, 400, 32);
    }

    public WaterMark(String waterMarkText, int width, int height, int size) {
        this.waterMarkText = waterMarkText;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public String getWaterMarkText() {
        return waterMarkText;
    }

    public void setWaterMarkText(String waterMarkText) {
        this.waterMarkText = waterMarkText;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
