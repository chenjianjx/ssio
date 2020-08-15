package org.ssio.internal.common.office.cellvalue.binder;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.internal.common.office.PoiSupport;

import java.time.LocalDateTime;

public class LocalDateTimeOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue((LocalDateTime)value);
        PoiSupport.setDateCellStyle(cell);
    }
}
