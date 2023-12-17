package org.ssio.api.impl.common.sheetlocate;

import org.ssio.spi.interfaces.model.read.ReadableSheet;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

public class SheetByIndexLocator implements SheetLocator {

    private int index;

    public SheetByIndexLocator(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Sheet index cannot be negative");
        }
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    @Override
    public ReadableSheet getSheet(ReadableWorkbook workbook) {
        return workbook.getSheetAt(index);
    }
}
