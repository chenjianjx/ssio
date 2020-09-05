package org.ssio.api.internal.common.sheetlocate;

import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

public class SsSheetByNameLocator implements SsSheetLocator {

    private String name;

    public SsSheetByNameLocator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public SsSheet getSheet(SsWorkbook workbook) {
        return workbook.getSheetByName(name);
    }
}
