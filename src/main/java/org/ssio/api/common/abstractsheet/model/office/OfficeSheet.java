package org.ssio.api.common.abstractsheet.model.office;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.common.abstractsheet.model.SsRow;
import org.ssio.api.common.abstractsheet.model.SsSheet;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeSheet implements SsSheet {

    private static final Logger logger = LoggerFactory.getLogger(OfficeSheet.class);

    private Sheet poiSheet;

    /**
     * key = rowIndex
     */
    private Map<Integer, OfficeRow> rows = new LinkedHashMap<>();

    public OfficeSheet(Sheet poiSheet) {
        this.poiSheet = poiSheet;
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
    public SsRow createRow(int rowIndex) {
        Row poiRow = poiSheet.createRow(rowIndex);
        OfficeRow row = new OfficeRow(poiRow);
        rows.put(rowIndex, row);
        return row;
    }

    @Override
    public int getNumberOfRows() {
        return poiSheet.getLastRowNum() + 1;
    }
}
