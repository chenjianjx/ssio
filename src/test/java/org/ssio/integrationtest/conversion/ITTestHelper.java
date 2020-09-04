package org.ssio.integrationtest.conversion;

import org.ssio.api.interfaces.SpreadsheetFileType;

public class ITTestHelper {
    public static String decideTargetFileExtension(SpreadsheetFileType spreadsheetFileType) {
        return spreadsheetFileType == SpreadsheetFileType.CSV ? ".csv" : ".xlsx";
    }
}
