package org.ssio.api.common.abstractsheet.model.office;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeWorkbook implements SsWorkbook {
    private Workbook poiBook;
    private List<OfficeSheet> sheets = new ArrayList<>();

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
        OfficeSheet sheet = new OfficeSheet(poiSheet);
        sheets.add(sheet);
        return sheet;
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        poiBook.write(outputTarget);
    }

    @Override
    public int getNumberOfSheets() {
        return sheets.size();
    }

    @Override
    public SsSheet getSheetByName(String sheetName) {
        return sheets.stream().filter(s -> sheetName.equals(s.getSheetName())).findFirst().orElse(null);
    }

    @Override
    public SsSheet getSheetAt(int sheetIndex) {
        Sheet poiSheet = poiBook.getSheetAt(sheetIndex);
        return new OfficeSheet(poiSheet);
    }
}
