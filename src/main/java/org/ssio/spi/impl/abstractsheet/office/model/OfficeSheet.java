package org.ssio.spi.impl.abstractsheet.office.model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.spi.interfaces.abstractsheet.model.SsRow;
import org.ssio.spi.interfaces.abstractsheet.model.SsSheet;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeSheet implements SsSheet {

    private static final Logger logger = LoggerFactory.getLogger(OfficeSheet.class);

    private Sheet poiSheet;

    /**
     * key = rowIndex
     */
    private Map<Integer, OfficeRow> rows = new LinkedHashMap<>();

    private OfficeSheet() {

    }

    public static OfficeSheet createEmptySheet(Workbook poiBook, String sheetName) {
        Sheet poiSheet = sheetName == null ? poiBook.createSheet() : poiBook.createSheet(sheetName);
        OfficeSheet sheet = new OfficeSheet();
        sheet.poiSheet = poiSheet;
        return sheet;
    }


    public static OfficeSheet createFromExistingPoiSheet(Sheet poiSheet) {
        OfficeSheet sheet = new OfficeSheet();
        sheet.poiSheet = poiSheet;

        int numOfRows = poiSheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row poiRow = poiSheet.getRow(i);
            OfficeRow row = OfficeRow.createFromExistingPoiRow(poiRow);
            sheet.rows.put(i, row);
        }

        return sheet;
    }


    @Override
    public void autoSizeColumn(int columnIndex) {
        poiSheet.autoSizeColumn(columnIndex);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getSheetName() {
        return poiSheet.getSheetName();
    }


    @Override
    public SsRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public SsRow createNewRow(int rowIndex) {
        OfficeRow row = OfficeRow.createEmptyRow(poiSheet, rowIndex);
        rows.put(rowIndex, row);
        return row;
    }

    @Override
    public int getNumberOfRows() {
        return rows.size();
    }
}
