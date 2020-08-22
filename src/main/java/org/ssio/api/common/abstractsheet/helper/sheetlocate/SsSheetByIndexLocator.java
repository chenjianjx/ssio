package org.ssio.api.common.abstractsheet.helper.sheetlocate;

import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;

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
