package org.ssio.spi.internal.filetypespecific.abstractsheet.office.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiSupport {

    public static void setDateCellStyle(Cell cell, String dateFormat) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateFormat));
        cell.setCellStyle(cellStyle);
    }

    public static String adaptSimpleDateFormatForPoi(String simpleDateFormat) {
        // single quote is not supported by office-like spreadsheets
        // TODO: More may need to be done
        return StringUtils.replace(simpleDateFormat, "'", "");
    }
}
