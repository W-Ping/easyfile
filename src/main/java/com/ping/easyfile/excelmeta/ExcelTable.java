package com.ping.easyfile.excelmeta;

import com.ping.easyfile.constant.FileConstant;
import com.ping.easyfile.core.handler.IWriteAfterHandler;
import com.ping.easyfile.core.handler.IWriteBeforeHandler;
import com.ping.easyfile.em.TableLayoutEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/5 12:17
 * @see
 */
public class ExcelTable implements Comparable<ExcelTable> {
    private int tableNo;
    private ExcelHeadProperty excelHeadProperty;
    private ExcelCellRange tableCellRange;
    private List<? extends BaseRowModel> data;
    private ExcelStyle tableStyle;
    private ExcelStyle tableHeadStyle;
    private List<ExcelColumnProperty> columnPropertyList = new ArrayList<>();
    private List<List<String>> head = new ArrayList<>();
    private Class<? extends BaseRowModel> headClass;
    private int firstRowIndex = 0;
    private int firstCellIndex = 0;
    private int startContentRowIndex = 0;
    private int spaceNum = 1;
    private TableLayoutEnum tableLayoutEnum = TableLayoutEnum.BOTTOM;
    private boolean needHead = true;
    private IWriteAfterHandler iWriteAfterHandler;
    private IWriteBeforeHandler iWriteBeforeHandler;

    public ExcelTable(int tableNo, List<List<String>> head, Class<? extends BaseRowModel> headClass, List<? extends BaseRowModel> data) {
        this(tableNo, head, headClass, true, data, 0, 0, null, null, null);
    }

    public ExcelTable(int tableNo, List<List<String>> head, Class<? extends BaseRowModel> headClass, List<? extends BaseRowModel> data, boolean needHead) {
        this(tableNo, head, headClass, needHead, data, 0, 0, null, null, null);
    }

    public ExcelTable(int tableNo, List<List<String>> head, Class<? extends BaseRowModel> headClass, List<? extends BaseRowModel> data, int firstRowIndex, int firstCellIndex) {
        this(tableNo, head, headClass, true, data, 0, 0, null, null, null);
    }

    public ExcelTable(int tableNo, List<List<String>> head, Class<? extends BaseRowModel> headClass, boolean needHead, List<? extends BaseRowModel> data, int firstRowIndex, int firstCellIndex, TableLayoutEnum tableLayoutEnum, IWriteBeforeHandler iWriteBeforeHandler, IWriteAfterHandler iWriteAfterHandler) {
        this.tableNo = tableNo <= 0 ? 1 : tableNo;
        this.head = head;
        this.headClass = headClass;
        this.data = data;
        this.needHead = needHead;
        this.firstRowIndex = firstRowIndex >= 0 ? firstRowIndex : 0;
        this.firstCellIndex = firstCellIndex >= 0 ? firstCellIndex : 0;
        this.iWriteBeforeHandler = iWriteBeforeHandler;
        this.iWriteAfterHandler = iWriteAfterHandler;
        this.tableLayoutEnum = tableLayoutEnum != null ? tableLayoutEnum : TableLayoutEnum.BOTTOM;
//        initTableProperties();

    }


    private void initTableProperties() {
        initExcelHeadProperty();
        initTableRange();
    }

    private void initExcelHeadProperty() {
        this.excelHeadProperty = new ExcelHeadProperty(this.headClass, head);
        if (CollectionUtils.isEmpty(this.head)) {
            this.head = this.excelHeadProperty.getHead();
        }
    }


    private void initTableRange() {
        int headRowNum = this.needHead ? this.excelHeadProperty.getHeadRowNum() : 0;
        this.startContentRowIndex = this.firstRowIndex + headRowNum;
        int lastRow = this.startContentRowIndex + (!CollectionUtils.isEmpty(this.data) ? this.data.size() : FileConstant.DEFAULT_ROW);
        this.tableCellRange = new ExcelCellRange(this.firstRowIndex, lastRow, this.firstCellIndex, this.head.size() + this.firstCellIndex);
    }


    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo <= 0 ? 1 : tableNo;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return excelHeadProperty;
    }

    public void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty) {
        this.excelHeadProperty = excelHeadProperty;
    }

    public ExcelCellRange getTableCellRange() {
        return tableCellRange;
    }

    public void setTableCellRange(ExcelCellRange tableCellRange) {
        this.tableCellRange = tableCellRange;
    }

    public List<? extends BaseRowModel> getData() {
        return data;
    }

    public void setData(List<? extends BaseRowModel> data) {
        this.data = data;
    }

    public ExcelStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(ExcelStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    public List<ExcelColumnProperty> getColumnPropertyList() {
        return columnPropertyList;
    }

    public void setColumnPropertyList(List<ExcelColumnProperty> columnPropertyList) {
        this.columnPropertyList = columnPropertyList;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class<? extends BaseRowModel> getHeadClass() {
        return headClass;
    }

    public void setHeadClass(Class<? extends BaseRowModel> headClass) {
        this.headClass = headClass;
    }

    public int getFirstRowIndex() {
        return firstRowIndex;
    }

    public void setFirstRowIndex(int firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
//        initTableRange();
    }

    public int getFirstCellIndex() {
        return firstCellIndex;
    }

    public void setFirstCellIndex(int firstCellIndex) {
        this.firstCellIndex = firstCellIndex;
//        initTableRange();
    }

    public int getStartContentRowIndex() {
        return startContentRowIndex;
    }

    public void setStartContentRowIndex(int startContentRowIndex) {
        this.startContentRowIndex = startContentRowIndex;
    }

    public ExcelStyle getTableHeadStyle() {
        return tableHeadStyle;
    }

    public void setTableHeadStyle(ExcelStyle tableHeadStyle) {
        this.tableHeadStyle = tableHeadStyle;
        if (this.excelHeadProperty != null) {
            this.excelHeadProperty.setHeadStyle(tableHeadStyle);
        }
    }

    public int getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(int spaceNum) {
        this.spaceNum = spaceNum >= 0 ? spaceNum : 0;
    }

    public TableLayoutEnum getTableLayoutEnum() {
        return tableLayoutEnum;
    }

    public void setTableLayoutEnum(TableLayoutEnum tableLayoutEnum) {
        if (tableLayoutEnum == null) {
            this.tableLayoutEnum = TableLayoutEnum.BOTTOM;
        } else {
            this.tableLayoutEnum = tableLayoutEnum;
        }
    }

    public boolean isNeedHead() {
        return needHead;
    }

    public void setNeedHead(boolean needHead) {
        this.needHead = needHead;
    }

    public IWriteAfterHandler getiWriteAfterHandler() {
        return iWriteAfterHandler;
    }

    public void setiWriteAfterHandler(IWriteAfterHandler iWriteAfterHandler) {
        this.iWriteAfterHandler = iWriteAfterHandler;
    }

    public IWriteBeforeHandler getiWriteBeforeHandler() {
        return iWriteBeforeHandler;
    }

    public void setiWriteBeforeHandler(IWriteBeforeHandler iWriteBeforeHandler) {
        this.iWriteBeforeHandler = iWriteBeforeHandler;
    }

    @Override
    public int compareTo(ExcelTable o) {
        int x = this.tableNo;
        int y = o.getTableNo();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
