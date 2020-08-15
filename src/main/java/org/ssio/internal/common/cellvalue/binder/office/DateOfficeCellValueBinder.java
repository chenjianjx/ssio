package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.office.PoiSupport;

import java.util.Date;

public class DateOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Date) value));


        PoiSupport.setDateCellStyle(cell, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
    }
}
