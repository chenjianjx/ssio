package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.office.PoiSupport;

import java.time.LocalDate;

public class LocalDateOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue((LocalDate)value);


        PoiSupport.setDateCellStyle(cell, SsioConstants.DEFAULT_LOCAL_DATE_PATTERN);
    }
}
