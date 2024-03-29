package com.ping.easyfile.excelmeta;

import com.ping.easyfile.util.FieldUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 18:55
 */
public class ExcelHeadProperty {
    /**
     * object class of table head
     */
    private Class<? extends BaseRowModel> headClazz;
    /**
     * table head
     */
    private List<List<String>> head = new ArrayList<>();
    /**
     * arguments of table head column
     */
    private List<ExcelColumnProperty> columnPropertyList = new ArrayList<>();
    /**
     * style of table head
     */
    private ExcelStyle headStyle;

    public ExcelHeadProperty(Class<? extends BaseRowModel> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        this.head = head;
        initHeadProperties();
    }

    private void initHeadProperties() {
        if (headClazz != null) {
            List<Field> objectField = FieldUtil.getObjectField(headClazz);
            if (!CollectionUtils.isEmpty(objectField)) {
                for (Field f : objectField) {
                    ExcelColumnProperty excelColumnProperty = FieldUtil.annotationToObject(f, ExcelColumnProperty.class);
                    if (excelColumnProperty != null) {
                        this.columnPropertyList.add(excelColumnProperty);
                    }
                }
            }
            Collections.sort(columnPropertyList);
            List<List<String>> headList = new ArrayList<>();
            if (head == null || head.size() == 0) {
                for (ExcelColumnProperty excelColumnProperty : columnPropertyList) {
                    headList.add(excelColumnProperty.getHead());
                }
                this.head = headList;
            }
        }
    }

    public int getHeadRowNum() {
        int headRowNum = 0;
        for (List<String> list : head) {
            if (list != null && list.size() > 0) {
                if (list.size() > headRowNum) {
                    headRowNum = list.size();
                }
            }
        }
        return headRowNum;
    }

    public List<ExcelCellRange> getCellRangeModels(int startCellIndex) {
        List<ExcelCellRange> cellRanges = new ArrayList<>();
        for (int i = 0; i < head.size(); i++) {
            List<String> columnValues = head.get(i);
            for (int j = 0; j < columnValues.size(); j++) {
                int lastRow = getLastRangNum(j, columnValues.get(j), columnValues);
                int lastColumn = getLastRangNum(i, columnValues.get(j), getHeadByRowNum(j));
                if ((lastRow > j || lastColumn > i) && lastRow >= 0 && lastColumn >= 0) {
                    cellRanges.add(new ExcelCellRange(j, lastRow, i + startCellIndex, lastColumn + startCellIndex));
                }
            }
        }
        return cellRanges;
    }

    private int getLastRangNum(int j, String value, List<String> values) {
        if (value == null) {
            return -1;
        }
        if (j > 0) {
            String preValue = values.get(j - 1);
            if (value.equals(preValue)) {
                return -1;
            }
        }
        int last = j;
        for (int i = last + 1; i < values.size(); i++) {
            String current = values.get(i);
            if (value.equals(current)) {
                last = i;
            } else {
                if (i > j) {
                    break;
                }
            }
        }
        return last;

    }

    public int getRowNum() {
        int headRowNum = 0;
        for (List<String> list : head) {
            if (list != null && list.size() > 0) {
                if (list.size() > headRowNum) {
                    headRowNum = list.size();
                }
            }
        }
        return headRowNum;
    }

    public List<String> getHeadByRowNum(int rowNum) {
        List<String> l = new ArrayList<String>(head.size());
        for (List<String> list : head) {
            if (list.size() > rowNum) {
                l.add(list.get(rowNum));
            } else {
                l.add(list.get(list.size() - 1));
            }
        }
        return l;
    }

    public Class<? extends BaseRowModel> getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class<? extends BaseRowModel> headClazz) {
        this.headClazz = headClazz;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public List<ExcelColumnProperty> getColumnPropertyList() {
        return columnPropertyList;
    }

    public void setColumnPropertyList(List<ExcelColumnProperty> columnPropertyList) {
        this.columnPropertyList = columnPropertyList;
    }

    public ExcelStyle getHeadStyle() {
        return headStyle;
    }

    public void setHeadStyle(ExcelStyle headStyle) {
        this.headStyle = headStyle;
    }
}
