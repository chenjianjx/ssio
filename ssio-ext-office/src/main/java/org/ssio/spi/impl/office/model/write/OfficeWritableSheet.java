package org.ssio.spi.impl.office.model.write;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.ssio.spi.interfaces.model.write.WritableSheet;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeWritableSheet implements WritableSheet {


    private Sheet poiSheet;

    /**
     * key = rowIndex.  Note a row can be null
     */
    private Map<Integer, OfficeWritableRow> rows = new LinkedHashMap<>();

    private OfficeWritableSheet() {

    }

    public static OfficeWritableSheet createEmptySheet(Workbook poiBook, String sheetName) {
        Sheet poiSheet = sheetName == null ? poiBook.createSheet() : poiBook.createSheet(sheetName);
        OfficeWritableSheet sheet = new OfficeWritableSheet();
        sheet.poiSheet = poiSheet;
        return sheet;
    }


    @Override
    public void autoSizeColumn(int columnIndex) {
        poiSheet.autoSizeColumn(columnIndex);
    }


    @Override
    public OfficeWritableRow createNewRow(int rowIndex) {
        OfficeWritableRow row = OfficeWritableRow.createEmptyRow(poiSheet, rowIndex);
        rows.put(rowIndex, row);
        return row;
    }
}
