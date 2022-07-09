package com.ping.easyfile.excelmeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 17:01
 */
public class ExcelColumnProperty extends BaseColumnProperty {

    private Field field;
    private int index = 99999;
    private List<String> head = new ArrayList<String>();

    /**
     */
    private String format;

    public ExcelColumnProperty() {
    }

    public ExcelColumnProperty(Field field, int index, List<String> head, String format) {
        this.field = field;
        this.index = index;
        this.head = head;
        this.format = format;
    }

    public ExcelColumnProperty(Field field, int index, List<String> head) {
        this.field = field;
        this.index = index;
        this.head = head;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public int compareTo(ExcelColumnProperty o) {
        int x = this.index;
        int y = o.getIndex();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
