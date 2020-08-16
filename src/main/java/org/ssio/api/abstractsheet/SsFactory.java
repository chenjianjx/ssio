package org.ssio.api.abstractsheet;

import org.ssio.api.SpreadsheetFileType;

public interface SsFactory {
    SsWorkbook newWorkbook(SpreadsheetFileType fileType, char cellSeparator);
}
