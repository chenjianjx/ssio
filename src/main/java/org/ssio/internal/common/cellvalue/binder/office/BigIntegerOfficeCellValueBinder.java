package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;

import java.math.BigInteger;

public class BigIntegerOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((BigInteger)value).doubleValue());
    }
}
