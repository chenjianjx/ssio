package org.ssio.internal.common.office;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.ssio.api.common.SsioConstants;

public class PoiSupport {

    public static void setDateCellStyle(Cell cell, String dateFormat) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateFormat));
        cell.setCellStyle(cellStyle);
    }
}
