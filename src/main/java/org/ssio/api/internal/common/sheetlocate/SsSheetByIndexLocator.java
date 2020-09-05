package org.ssio.api.internal.common.sheetlocate;

import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

public class SsSheetByIndexLocator implements SsSheetLocator {

    private int index;

    public SsSheetByIndexLocator(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Sheet index cannot be negative");
        }
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    @Override
    public SsSheet getSheet(SsWorkbook workbook) {
        return workbook.getSheetAt(index);
    }
}
