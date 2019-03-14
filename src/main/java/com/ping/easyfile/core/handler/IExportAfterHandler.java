package com.ping.easyfile.core.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface IExportAfterHandler {
    /**
     * @param sheetNo
     * @param sheet
     */
    void sheet(int sheetNo, Sheet sheet);

    /**
     * @param rowNum
     * @param row
     */
    void row(int rowNum, Row row);

    /**
     * @param cellNum
     * @param cell
     */
    void cell(int cellNum, Cell cell);
}
