package org.ssio.api.abstractsheet;

import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.abstractsheet.csv.CsvWorkbook;
import org.ssio.api.abstractsheet.office.OfficeWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class DefaultSsFactory implements SsFactory {

    @Override
    public SsWorkbook newWorkbook(SpreadsheetFileType fileType, char cellSeparator) {
        if (fileType == null) {
            throw new IllegalArgumentException();
        }

        switch (fileType) {
            case CSV:
                return new CsvWorkbook(cellSeparator);

            case OFFICE:
                return OfficeWorkbook.createNewWorkbook();

            default:
                throw new IllegalArgumentException("Unsupported spreadsheet file type: " + fileType);
        }
    }

    @Override
    public SsWorkbook createWorkbookFromInput(SpreadsheetFileType fileType, InputStream spreadsheetInput) throws IOException {
        if (fileType == null) {
            throw new IllegalArgumentException();
        }

        switch (fileType) {
            case CSV:
                throw new UnsupportedOperationException();

            case OFFICE:
                return OfficeWorkbook.createFromInput(spreadsheetInput);

            default:
                throw new IllegalArgumentException("Unsupported spreadsheet file type: " + fileType);
        }
    }

}
