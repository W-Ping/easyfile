package com.ping.easyfile.excelmeta;

/**
 * @author liu_wp
 * @date Created in 2019/3/22 14:26
 * @see
 */
public class ExcelReadTable {
    private int tableNo;
    private Class<? extends BaseRowModel> dataModelClass;
    private ExcelCellRange excelCellRange;
    private int sheetNo;


    public Class<? extends BaseRowModel> getDataModelClass() {
        return dataModelClass;
    }

    public void setDataModelClass(Class<? extends BaseRowModel> dataModelClass) {
        this.dataModelClass = dataModelClass;
    }

    public ExcelCellRange getExcelCellRange() {
        return excelCellRange;
    }

    public void setExcelCellRange(ExcelCellRange excelCellRange) {
        this.excelCellRange = excelCellRange;
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
}
