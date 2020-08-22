package org.ssio.api.common.abstractsheet.helper.sheetlocate;

import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;

public class SsSheetByNameLocator implements SsSheetLocator {

    private String name;

    public SsSheetByNameLocator(String index) {
        this.name = index;
    }

    public String getName() {
        return name;
    }


    @Override
    public SsSheet getSheet(SsWorkbook workbook) {
        return workbook.getSheetByName(name);
    }
}
