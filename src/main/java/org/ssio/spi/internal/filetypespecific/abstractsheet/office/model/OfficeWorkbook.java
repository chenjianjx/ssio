package org.ssio.spi.internal.filetypespecific.abstractsheet.office.model;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.api.internal.common.sheetlocate.SsSheetLocator;
import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeWorkbook implements SsWorkbook {
    private Workbook poiBook;
    private List<OfficeSheet> sheets = new ArrayList<>();

    private String sheetNameForSave;
    private OfficeSheet sheetToParse;

    private OfficeWorkbook() {

    }

    public static OfficeWorkbook createNewWorkbook(String sheetNameForSave) {
        Workbook poiBook = new XSSFWorkbook();
        OfficeWorkbook workbook = new OfficeWorkbook();
        workbook.poiBook = poiBook;
        workbook.sheetNameForSave = sheetNameForSave;
        return workbook;
    }

    public static OfficeWorkbook createFromInput(InputStream spreadsheetInput, SsSheetLocator sheetLocator) throws IOException {
        Workbook poiBook = WorkbookFactory.create(spreadsheetInput);
        OfficeWorkbook workbook = new OfficeWorkbook();
        workbook.poiBook = poiBook;
        for (int i = 0; i < poiBook.getNumberOfSheets(); i++) {
            workbook.sheets.add(OfficeSheet.createFromExistingPoiSheet(poiBook.getSheetAt(i)));
        }

        workbook.sheetToParse = (OfficeSheet) sheetLocator.getSheet(workbook);
        if (workbook.sheetToParse == null) {
            throw new IllegalArgumentException("No sheet found using locator: " + sheetLocator.getDesc());
        }
        return workbook;
    }

    @Override
    public SsSheet createNewSheet() {
        OfficeSheet sheet = OfficeSheet.createEmptySheet(poiBook, sheetNameForSave);
        sheets.add(sheet);
        return sheet;
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        poiBook.write(outputTarget);
    }

    @Override
    public SsSheet getSheetToParse() {
        return this.sheetToParse;
    }


    @Override
    public SsSheet getSheetByName(String sheetName) {
        return sheets.stream().filter(s -> sheetName.equals(s.getSheetName())).findFirst().orElse(null);
    }

    @Override
    public SsSheet getSheetAt(int sheetIndex) {
        return sheets.get(sheetIndex);
    }
}
