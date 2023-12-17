package org.ssio.api.impl.common.sheetlocate;

import org.ssio.spi.interfaces.model.read.ReadableSheet;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

public class SheetByNameLocator implements SheetLocator {

    private String name;

    public SheetByNameLocator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public ReadableSheet getSheet(ReadableWorkbook workbook) {
        return workbook.getSheetByName(name);
    }
}
