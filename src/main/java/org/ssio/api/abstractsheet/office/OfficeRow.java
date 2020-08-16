package org.ssio.api.abstractsheet.office;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.abstractsheet.SsCell;
import org.ssio.api.abstractsheet.SsRow;

public class OfficeRow implements SsRow {
    private final Row poiRow;

    public OfficeRow(Row poiRow) {
        this.poiRow = poiRow;
    }

    @Override
    public int getNumberOfCells() {
        return poiRow.getLastCellNum();
    }

    @Override
    public SsCell getCell(int columnIndex) {
        Cell poiCell = poiRow.getCell(columnIndex);
        return new OfficeCell(poiCell);
    }
}
