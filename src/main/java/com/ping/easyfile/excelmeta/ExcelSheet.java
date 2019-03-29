package com.ping.easyfile.excelmeta;

import com.ping.easyfile.em.TableLayoutEnum;
import com.ping.easyfile.exception.ExcelParseException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * @author liu_wp
 * @date Created in 2019/3/5 12:12
 * @see
 */
public class ExcelSheet {
    private String sheetName;
    private int sheetNo;
    private boolean isAutoLayOut = true;
    private List<ExcelTable> excelTables;
    private Map<Integer, Integer> columnWidthMap = new HashMap<>();

    /**
     * Integer[]={startRow,lastRow,startCell,endCell}
     */
    private List<Integer[]> mergeData;

    public ExcelSheet(int sheetNo, String sheetName, List<ExcelTable> excelTables, boolean isAutoLayOut) {
        this.sheetName = sheetName;
        this.sheetNo = sheetNo >= 0 ? sheetNo : 0;
        this.excelTables = excelTables;
    }

    public ExcelSheet(int sheetNo, String sheetName, List<ExcelTable> excelTables) {
        this(sheetNo, sheetName, excelTables, true);
    }

    public void initCurrentSheet() {
        if (!CollectionUtils.isEmpty(excelTables) && excelTables.size() > 1) {
            Collections.sort(this.excelTables);
            if (isAutoLayOut) {
                for (int i = 0; i < this.excelTables.size(); i++) {
                    if (i > 0) {
                        ExcelTable currTable = this.excelTables.get(i);
                        TableLayoutEnum tableLayoutEnum = currTable.getTableLayoutEnum();
                        if (tableLayoutEnum == null) {
                            continue;
                        }
                        ExcelCellRange currTableCellRange = currTable.getTableCellRange();
                        ExcelTable maxRowTable = getMaxRowTable(i - 1);
                        int rowNum = currTableCellRange.getLastRowIndex() - currTableCellRange.getFirstRowIndex();
                        int headRowNum = currTable.isNeedHead() ? currTable.getExcelHeadProperty().getHeadRowNum() : 0;
                        if (TableLayoutEnum.RIGHT.equals(tableLayoutEnum)) {
                            ExcelTable preTable = this.excelTables.get(i - 1);
                            ExcelCellRange preTableCellRange = preTable.getTableCellRange();
                            currTableCellRange.setFirstCellIndex(preTableCellRange.getLastCellIndex() + currTable.getSpaceNum());
                            currTableCellRange.setLastCellIndex(currTableCellRange.getLastCellIndex() + currTableCellRange.getFirstCellIndex());
                            currTableCellRange.setFirstRowIndex(preTableCellRange.getFirstRowIndex());
                            currTableCellRange.setLastRowIndex(currTableCellRange.getFirstRowIndex() + rowNum);
                            currTable.setFirstCellIndex(currTableCellRange.getFirstCellIndex());
                            currTable.setFirstRowIndex(currTableCellRange.getFirstRowIndex());
                        } else if (TableLayoutEnum.BOTTOM.equals(tableLayoutEnum)) {
                            currTableCellRange.setFirstRowIndex(maxRowTable.getTableCellRange().getLastRowIndex() + currTable.getSpaceNum());
                            currTableCellRange.setLastRowIndex(currTableCellRange.getFirstRowIndex() + rowNum);
                            currTable.setFirstRowIndex(currTableCellRange.getFirstRowIndex());
                        }
                        currTable.setStartContentRowIndex(currTableCellRange.getFirstRowIndex() + headRowNum);
                        currTable.setTableCellRange(currTableCellRange);
                    }
                }
            }
        }
    }

    private ExcelTable getMaxCellTable(int num) {
        ExcelTable excelTable = null;
        if (num >= this.excelTables.size()) {
            num = this.excelTables.size() - 1;
        }
        for (int j = num; j >= 0; j--) {
            ExcelTable currTable = this.excelTables.get(j);
            if (!TableLayoutEnum.RIGHT.equals(currTable.getTableLayoutEnum())) {
                if (excelTable == null) {
                    excelTable = currTable;
                }
                break;
            }
            if (j >= 1) {
                ExcelTable preTable = this.excelTables.get(j - 1);
                if (excelTable != null) {
                    currTable = excelTable;
                }
                if (currIsMax(currTable.getTableCellRange().getLastCellIndex(), preTable.getTableCellRange().getLastCellIndex())) {
                    excelTable = currTable;
                } else {
                    excelTable = preTable;
                }
            }
        }
        return excelTable;
    }

    private ExcelTable getMaxRowTable(int num) {
        ExcelTable excelTable = null;
        if (num >= this.excelTables.size()) {
            num = this.excelTables.size() - 1;
        }
        for (int j = num; j >= 0; j--) {
            ExcelTable currTable = this.excelTables.get(j);
            if (j >= 1) {
                ExcelTable preTable = this.excelTables.get(j - 1);
                if (excelTable != null) {
                    currTable = excelTable;
                }
                if (currIsMax(currTable.getTableCellRange().getLastRowIndex(), preTable.getTableCellRange().getLastRowIndex())) {
                    excelTable = currTable;
                } else {
                    excelTable = preTable;
                }
            }
        }
        if (excelTable == null) {
            excelTable = this.excelTables.get(0);
        }
        return excelTable;
    }

    private boolean currIsMax(int a, int b) {
        if (a > b) {
            return true;
        }
        return false;
    }


    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo >= 0 ? sheetNo : 0;
    }

    public List<ExcelTable> getExcelTables() {
        return excelTables;
    }

    public void setExcelTables(List<ExcelTable> excelTables) {
        this.excelTables = excelTables;
    }

    public Map<Integer, Integer> getColumnWidthMap() {
        return columnWidthMap;
    }

    public void setColumnWidthMap(Map<Integer, Integer> columnWidthMap) {
        this.columnWidthMap = columnWidthMap;
    }

    public boolean isAutoLayOut() {
        return isAutoLayOut;
    }

    public void setAutoLayOut(boolean autoLayOut) {
        isAutoLayOut = autoLayOut;
    }

    public List<Integer[]> getMergeData() {
        return mergeData;
    }

    public void setMergeData(List<Integer[]> mergeData) {
        if (mergeData != null && mergeData.stream().filter(v -> v.length != 4).findAny().isPresent()) {
            throw new ExcelParseException("array of Integers can only be 4 sizes in mergeData ");
        }
        this.mergeData = mergeData;
    }

    public static void main(String[] args) {
        Set<Integer[]> ss = new HashSet<>();
        Integer[] a = {1, 2, 3, 4};
        Integer[] ad = {1, 2, 5, 4};
        ss.add(a);
        ss.add(ad);
//        Optional<Integer[]> any = ss.stream().filter(v -> v.length != 4).findAny();
        boolean present = ss.stream().filter(v -> v.length != 4).findAny().isPresent();
//        Integer[] integers = any.get();
//        System.out.println(JSONUtil.objectToString(integers));
        System.out.println(present);
    }
}
