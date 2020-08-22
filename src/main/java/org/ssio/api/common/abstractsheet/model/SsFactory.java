package org.ssio.api.common.abstractsheet.model;

import org.ssio.api.SpreadsheetFileType;

import java.io.IOException;
import java.io.InputStream;

public interface SsFactory {
    SsWorkbook newWorkbook(SpreadsheetFileType fileType, char cellSeparator);

    SsWorkbook createWorkbookFromInput(SpreadsheetFileType fileType, InputStream spreadsheetInput, String inputCharset, char cellSeparator) throws IOException;
}
