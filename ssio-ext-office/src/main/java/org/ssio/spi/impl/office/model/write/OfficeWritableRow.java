package org.ssio.spi.impl.office.model.write;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ssio.spi.interfaces.model.write.WritableCell;
import org.ssio.spi.interfaces.model.write.WritableRow;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeWritableRow implements WritableRow {
    private Row poiRow;

    /**
     * key = columnIndex
     */
    private Map<Integer, OfficeWritableCell> cells = new LinkedHashMap<>();

    private OfficeWritableRow() {

    }

    public static OfficeWritableRow createEmptyRow(Sheet poiSheet, int rowIndex) {
        Row poiRow = poiSheet.createRow(rowIndex);
        OfficeWritableRow row = new OfficeWritableRow();
        row.poiRow = poiRow;
        return row;
    }
    



    @Override
    public WritableCell createCell(int columnIndex) {
        OfficeWritableCell cell = OfficeWritableCell.createEmptyCell(poiRow, columnIndex);
        cells.put(columnIndex, cell);
        return cell;
    }
}
