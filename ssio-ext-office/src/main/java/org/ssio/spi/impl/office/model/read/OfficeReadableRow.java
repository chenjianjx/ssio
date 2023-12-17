package org.ssio.spi.impl.office.model.read;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ssio.spi.interfaces.model.read.ReadableCell;
import org.ssio.spi.interfaces.model.read.ReadableRow;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeReadableRow implements ReadableRow {

    /**
     * key = columnIndex
     */
    private Map<Integer, OfficeReadableCell> cells = new LinkedHashMap<>();

    private OfficeReadableRow() {

    }

    public static OfficeReadableRow newInstanceFromPoiRow(Row poiRow) {
        if (poiRow == null) {
            return null;
        }

        OfficeReadableRow row = new OfficeReadableRow();

        int numOfCells = poiRow.getLastCellNum();
        for (int i = 0; i < numOfCells; i++) {
            Cell poiCell = poiRow.getCell(i);
            row.cells.put(i, OfficeReadableCell.newInstanceFromPoiCell(poiCell));
        }

        return row;
    }

    @Override
    public int getNumberOfCells() {
        return cells.size();
    }

    @Override
    public ReadableCell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }

}
