package com.iris.excelfile.core.builder;

import com.iris.excelfile.metadata.ExcelSheet;
import com.iris.excelfile.metadata.ExcelTable;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 10:14
 * @see
 */
public interface IWriteBuilder {

    /**
     * @param excelSheet
     */
    void addContent(ExcelSheet excelSheet);


    /**
     * @param table
     */
    void addContent(ExcelTable table);

    /**
     * @param startRow
     * @param endRow
     * @param startCell
     * @param endCell
     */
    void merge(int startRow, int endRow, int startCell, int endCell);

    /**
     *
     */
    void finish();
}
