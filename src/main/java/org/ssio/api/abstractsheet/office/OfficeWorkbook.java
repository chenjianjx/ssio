package org.ssio.api.abstractsheet.office;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.abstractsheet.SsWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OfficeWorkbook implements SsWorkbook {
    private Workbook poiBook;

    private OfficeWorkbook(Workbook poiBook) {
        this.poiBook = poiBook;
    }

    public static OfficeWorkbook createNewWorkbook() {
        Workbook poiBook = new XSSFWorkbook();
        return new OfficeWorkbook(poiBook);
    }

    public static SsWorkbook createFromInput(InputStream spreadsheetInput) throws IOException {
        Workbook poiBook = WorkbookFactory.create(spreadsheetInput);
        return new OfficeWorkbook(poiBook);
    }

    @Override
    public SsSheet createSheet(String sheetName) {
        Sheet poiSheet = sheetName == null ? poiBook.createSheet() : poiBook.createSheet(sheetName);
        return new OfficeSheet(poiSheet);
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        poiBook.write(outputTarget);
    }

    @Override
    public int getNumberOfSheets() {
        return poiBook.getNumberOfSheets();
    }

    @Override
    public SsSheet getSheetByName(String sheetName) {
        Sheet poiSheet = poiBook.getSheet(sheetName);
        return poiSheet == null ? null : new OfficeSheet(poiSheet);
    }

    @Override
    public SsSheet getSheetAt(int sheetIndex) {
        Sheet poiSheet = poiBook.getSheetAt(sheetIndex);
        return new OfficeSheet(poiSheet);
    }
}
