package org.ssio.api.abstractsheet.office;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.abstractsheet.SsWorkbook;

import java.io.IOException;
import java.io.OutputStream;

public class OfficeWorkbook implements SsWorkbook {
    private Workbook poiBook;

    public OfficeWorkbook() {
        this.poiBook = new XSSFWorkbook();
    }

    @Override
    public SsSheet createSheet(String sheetName) {
        Sheet poiSheet = poiBook.createSheet(sheetName);
        return new OfficeSheet(poiSheet);
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        poiBook.write(outputTarget);
    }
}
