package com.iris.excelfile.metadata;

/**
 * @author liu_wp
 * @date Created in 2019/6/17 13:25
 * @see
 */
public class ExcelShiftRange {
    private int startRow;
    private int endRow;
    private int shiftNumber = 1;
    private boolean copyRowHeight;
    private boolean resetOriginalRowHeight;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        if (shiftNumber <= 0) {
            shiftNumber = 1;
        }
        this.shiftNumber = shiftNumber;
    }

    public boolean isCopyRowHeight() {
        return copyRowHeight;
    }

    public void setCopyRowHeight(boolean copyRowHeight) {
        this.copyRowHeight = copyRowHeight;
    }

    public boolean isResetOriginalRowHeight() {
        return resetOriginalRowHeight;
    }

    public void setResetOriginalRowHeight(boolean resetOriginalRowHeight) {
        this.resetOriginalRowHeight = resetOriginalRowHeight;
    }
}
