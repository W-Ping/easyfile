package com.ping.easyfile.handler;

import com.ping.easyfile.core.handler.IWriteAfterHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author liu_wp
 * @date Created in 2019/3/29 15:46
 * @see
 */
public class WriteAfterHandlerImpl implements IWriteAfterHandler {

    @Override
    public void row(int rowNum, Row row) {
        System.out.println("rowNum:" + rowNum);
    }
    @Override
    public void cell(int cellNum, Cell cell) {
        System.out.println("cellNum:" + cellNum);
    }
}
