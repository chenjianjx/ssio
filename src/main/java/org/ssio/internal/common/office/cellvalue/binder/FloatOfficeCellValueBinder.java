package org.ssio.internal.common.office.cellvalue.binder;

import org.apache.poi.ss.usermodel.Cell;

public class FloatOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Float)value).doubleValue());
    }
}
