package org.ssio.spi.interfaces.abstractsheet.model;

import org.ssio.api.interfaces.SpreadsheetFileType;

import java.io.IOException;
import java.io.InputStream;

public interface SsWorkbookFactory {


    SsWorkbook createWorkbookFromInput(SpreadsheetFileType fileType, InputStream spreadsheetInput, String inputCharset, char cellSeparator) throws IOException;
}
