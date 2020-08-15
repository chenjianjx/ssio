package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;

public class DoubleOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Double)value));
    }

}
