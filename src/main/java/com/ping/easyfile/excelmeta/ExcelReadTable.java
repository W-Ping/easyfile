package com.ping.easyfile.excelmeta;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 14:26
 * @see
 */
public class ExcelReadTable {
    private int tableNo;
    private int sheetNo;
    private Class<? extends BaseRowModel> dataModelClass;
    private int startCellIndex;
    private int startRowIndex = 1;
    private Integer lastCellIndex;
    private Integer lastRowIndex;

    public ExcelReadTable(int tableNo, int sheetNo, Integer lastCellIndex) {
        this.tableNo = tableNo;
        this.sheetNo = sheetNo;
        this.lastCellIndex = lastCellIndex;
    }

    public Class<? extends BaseRowModel> getDataModelClass() {
        return dataModelClass;
    }

    public void setDataModelClass(Class<? extends BaseRowModel> dataModelClass) {
        this.dataModelClass = dataModelClass;
    }

    public int getStartCellIndex() {
        return startCellIndex;
    }

    public void setStartCellIndex(int startCellIndex) {
        this.startCellIndex = startCellIndex;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getLastCellIndex() {
        return lastCellIndex;
    }

    public void setLastCellIndex(Integer lastCellIndex) {
        this.lastCellIndex = lastCellIndex;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Integer getLastRowIndex() {
        return lastRowIndex;
    }

    public void setLastRowIndex(Integer lastRowIndex) {
        this.lastRowIndex = lastRowIndex;
    }
}
