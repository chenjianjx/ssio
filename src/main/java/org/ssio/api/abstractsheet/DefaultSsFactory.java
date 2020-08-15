package org.ssio.api.abstractsheet;

import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.abstractsheet.csv.CsvWorkbook;
import org.ssio.api.abstractsheet.office.OfficeWorkbook;

public class DefaultSsFactory implements SsFactory {

    @Override
    public SsWorkbook newWorkbook(SpreadsheetFileType fileType) {
        if (fileType == null) {
            throw new IllegalArgumentException();
        }

        switch (fileType) {
            case CSV:
                return new CsvWorkbook();

            case OFFICE:
                return new OfficeWorkbook();

            default:
                throw new IllegalArgumentException("Unsupported spreadsheet file type: " + fileType);
        }
    }

}
