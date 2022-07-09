package com.ping.easyfile.excelmeta;

/**
 * Excel 解析对象
 *
 * @author liu_wp
 * @date Created in 2019/3/22 14:26
 */
public class ExcelReadTable implements Comparable<ExcelReadTable> {
    /**
     * 解析的sheet 默认0
     */
    private int sheetNo = 0;
    /**
     * 唯一表编号 （Excel的数据是多张表格组成的）
     */
    private int tableNo;
    /**
     * 解析Excel Model（当前表格对应的Model）
     */
    private Class<? extends BaseRowModel> dataModelClass;
    /**
     * 开始单元个索引
     */
    private int startCellIndex;
    /**
     * 最后单元格索引
     */
    private Integer lastCellIndex;
    /**
     * 开始行索引
     */
    private int startRowIndex;
    /**
     * 最后一行索引
     */
    private Integer lastRowIndex;
    /**
     * 表头占用的行数 默认1行
     */
    private int headSize = 1;

    public ExcelReadTable(int tableNo) {
        this.tableNo = tableNo <= 0 ? 1 : tableNo;
    }

    public ExcelReadTable(int tableNo, Integer startCellIndex, Integer lastCellIndex) {
        this(tableNo, 0, startCellIndex, lastCellIndex);
    }

    public ExcelReadTable(int tableNo, int sheetNo, Integer startCellIndex, Integer lastCellIndex) {
        this.tableNo = tableNo <= 0 ? 1 : tableNo;
        this.sheetNo = sheetNo;
        this.startCellIndex = startCellIndex;
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

    @Override
    public int compareTo(ExcelReadTable o) {
        int x = this.tableNo;
        int y = o.getTableNo();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    public void setLastCellIndex(Integer lastCellIndex) {
        this.lastCellIndex = lastCellIndex;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo <= 0 ? 1 : tableNo;
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

    public int getHeadSize() {
        return headSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

}
