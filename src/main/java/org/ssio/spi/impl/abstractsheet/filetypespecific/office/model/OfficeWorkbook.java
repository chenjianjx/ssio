package org.ssio.spi.impl.abstractsheet.filetypespecific.office.model;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.spi.interfaces.abstractsheet.model.SsSheet;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeWorkbook implements SsWorkbook {
    private Workbook poiBook;
    private List<OfficeSheet> sheets = new ArrayList<>();

    private String sheetNameForSave;

    private OfficeWorkbook() {

    }

    public static OfficeWorkbook createNewWorkbook(String sheetNameForSave) {
        Workbook poiBook = new XSSFWorkbook();
        OfficeWorkbook workbook = new OfficeWorkbook();
        workbook.poiBook = poiBook;
        workbook.sheetNameForSave = sheetNameForSave;
        return workbook;
    }

    public static OfficeWorkbook createFromInput(InputStream spreadsheetInput) throws IOException {
        Workbook poiBook = WorkbookFactory.create(spreadsheetInput);
        OfficeWorkbook workbook = new OfficeWorkbook();
        workbook.poiBook = poiBook;
        for (int i = 0; i < poiBook.getNumberOfSheets(); i++) {
            workbook.sheets.add(OfficeSheet.createFromExistingPoiSheet(poiBook.getSheetAt(i)));
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
    public int getNumberOfSheets() {
        return sheets.size();
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