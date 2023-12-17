package org.ssio.spi.impl.office.model.read;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ssio.api.impl.common.sheetlocate.SheetLocator;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeReadableWorkbook implements ReadableWorkbook {

    private List<OfficeReadableSheet> sheets = new ArrayList<>();

    private OfficeReadableSheet sheetToParse;

    private OfficeReadableWorkbook() {

    }

    public static OfficeReadableWorkbook newInstanceFromInput(InputStream spreadsheetInput, SheetLocator sheetLocator) throws IOException {
        Workbook poiBook = WorkbookFactory.create(spreadsheetInput);
        OfficeReadableWorkbook workbook = new OfficeReadableWorkbook();
        for (int i = 0; i < poiBook.getNumberOfSheets(); i++) {
            workbook.sheets.add(OfficeReadableSheet.newInstanceFromExistingPoiSheet(poiBook.getSheetAt(i)));
        }

        workbook.sheetToParse = (OfficeReadableSheet) sheetLocator.getSheet(workbook);
        if (workbook.sheetToParse == null) {
            throw new IllegalArgumentException("No sheet found using locator: " + sheetLocator.getDesc());
        }
        return workbook;
    }


    @Override
    public OfficeReadableSheet getSheetToParse() {
        return this.sheetToParse;
    }


    @Override
    public OfficeReadableSheet getSheetByName(String sheetName) {
        return sheets.stream().filter(s -> sheetName.equals(s.getSheetName())).findFirst().orElse(null);
    }

    @Override
    public OfficeReadableSheet getSheetAt(int sheetIndex) {
        return sheets.get(sheetIndex);
    }

}
