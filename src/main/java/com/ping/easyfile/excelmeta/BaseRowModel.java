package com.ping.easyfile.excelmeta;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_wp
 * @date Created in 2019/3/5 10:53
 */
public class BaseRowModel {

    /**
     * 每列样式
     */
    private transient Map<Integer,CellStyle> cellStyleMap = new HashMap<Integer,CellStyle>();

    public void addStyle(Integer row, CellStyle cellStyle){
        cellStyleMap.put(row,cellStyle);
    }

    public CellStyle getStyle(Integer row){
        CellStyle cellStyle = cellStyleMap.get(row);
        return cellStyle;
    }

    public Map<Integer, CellStyle> getCellStyleMap() {
        return cellStyleMap;
    }

    public void setCellStyleMap(Map<Integer, CellStyle> cellStyleMap) {
        this.cellStyleMap = cellStyleMap;
    }
}
