package org.ssio.api.common.abstractsheet.model;

import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.abstractsheet.model.csv.CsvWorkbook;
import org.ssio.api.common.abstractsheet.model.office.OfficeWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultSsFactory implements SsFactory {

    @Override
    public SsWorkbook newWorkbook(SpreadsheetFileType fileType, char cellSeparator) {
        if (fileType == null) {
            throw new IllegalArgumentException();
        }

        switch (fileType) {
            case CSV:
                return CsvWorkbook.createNewWorkbook(cellSeparator);

            case OFFICE:
                return OfficeWorkbook.createNewWorkbook();

            default:
                throw new IllegalArgumentException("Unsupported spreadsheet file type: " + fileType);
        }
    }

    @Override
    public SsWorkbook createWorkbookFromInput(SpreadsheetFileType fileType, InputStream spreadsheetInput, String inputCharset, char cellSeparator) throws IOException {
        if (fileType == null) {
            throw new IllegalArgumentException();
        }

        switch (fileType) {
            case CSV:
                return CsvWorkbook.createFromInput(new InputStreamReader(spreadsheetInput, inputCharset), cellSeparator);

            case OFFICE:
                return OfficeWorkbook.createFromInput(spreadsheetInput);

            default:
                throw new IllegalArgumentException("Unsupported spreadsheet file type: " + fileType);
        }
    }

}
