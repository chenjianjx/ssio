package org.ssio.spi.impl.office.model.write;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.spi.interfaces.model.write.WritableWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeWritableWorkbook implements WritableWorkbook {
    private Workbook poiBook;
    private String sheetName;

    private List<OfficeWritableSheet> sheets = new ArrayList<>();

    private OfficeWritableWorkbook() {
    }

    public static OfficeWritableWorkbook createNewWorkbook(String sheetNameForSave) {
        Workbook poiBook = new XSSFWorkbook();
        OfficeWritableWorkbook workbook = new OfficeWritableWorkbook();
        workbook.poiBook = poiBook;
        workbook.sheetName = sheetNameForSave;
        return workbook;
    }

    @Override
    public OfficeWritableSheet createNewSheet() {
        OfficeWritableSheet sheet = OfficeWritableSheet.createEmptySheet(poiBook, sheetName);
        sheets.add(sheet);
        return sheet;
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        poiBook.write(outputTarget);
    }
}
