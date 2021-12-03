package org.ssio.spi.internal.filetypespecific.abstractsheet.office.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;
import org.ssio.spi.developerexternal.abstractsheet.model.SsRow;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeRow implements SsRow {
    private Row poiRow;

    /**
     * key = columnIndex
     */
    private Map<Integer, OfficeCell> cells = new LinkedHashMap<>();

    private OfficeRow() {

    }

    public static OfficeRow createEmptyRow(Sheet poiSheet, int rowIndex) {
        Row poiRow = poiSheet.createRow(rowIndex);
        OfficeRow row = new OfficeRow();
        row.poiRow = poiRow;
        return row;
    }

    public static OfficeRow createFromExistingPoiRow(Row poiRow) {
        OfficeRow row = new OfficeRow();
        row.poiRow = poiRow;

        if (poiRow == null) {
            return null;
        }

        int numOfCells = poiRow.getLastCellNum();
        for (int i = 0; i < numOfCells; i++) {
            Cell poiCell = poiRow.getCell(i);
            row.cells.put(i, OfficeCell.createFromExistingPoiCell(poiCell));
        }

        return row;
    }

    @Override
    public int getNumberOfCells() {
        return cells.size();
    }

    @Override
    public SsCell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }

    @Override
    public SsCell createCell(int columnIndex) {
        OfficeCell cell = OfficeCell.createEmptyCell(poiRow, columnIndex);
        cells.put(columnIndex, cell);
        return cell;
    }
}
