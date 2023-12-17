package org.ssio.spi.impl.office.model.read;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ssio.spi.interfaces.model.read.ReadableSheet;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeReadableSheet implements ReadableSheet {


    private Sheet poiSheet;

    /**
     * key = rowIndex.  Note a row can be null
     */
    private Map<Integer, OfficeReadableRow> rows = new LinkedHashMap<>();

    private OfficeReadableSheet() {

    }

    public static OfficeReadableSheet newInstanceFromExistingPoiSheet(Sheet poiSheet) {
        OfficeReadableSheet sheet = new OfficeReadableSheet();
        sheet.poiSheet = poiSheet;

        int numOfRows = poiSheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row poiRow = poiSheet.getRow(i);
            OfficeReadableRow row = OfficeReadableRow.newInstanceFromPoiRow(poiRow);
            sheet.rows.put(i, row);
        }

        return sheet;
    }



    @Override
    public String getSheetName() {
        return poiSheet.getSheetName();
    }


    @Override
    public OfficeReadableRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }


    @Override
    public int getNumberOfRows() {
        return rows.size();
    }
}
