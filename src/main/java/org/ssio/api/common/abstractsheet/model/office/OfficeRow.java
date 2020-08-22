package org.ssio.api.common.abstractsheet.model.office;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsRow;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficeRow implements SsRow {
    private final Row poiRow;

    /**
     * key = columnIndex
     */
    private Map<Integer, OfficeCell> cells = new LinkedHashMap<>();

    public OfficeRow(Row poiRow) {
        this.poiRow = poiRow;
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
        Cell poiCell = poiRow.createCell(columnIndex);
        OfficeCell cell = new OfficeCell(poiCell);
        cells.put(columnIndex, cell);
        return cell;
    }
}
