package org.ssio.api.abstractsheet.office;

import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.abstractsheet.SsRow;

public class OfficeRow implements SsRow {
    private Row poiRow;

    public OfficeRow(Row poiRow) {
        this.poiRow = poiRow;
    }
}
