package org.ssio.integrationtest.conversion;

import org.ssio.api.SpreadsheetFileType;

public class ConversionITTestHelper {
    public static String decideTargetFileExtension(SpreadsheetFileType spreadsheetFileType) {
        return spreadsheetFileType == SpreadsheetFileType.CSV ? ".csv" : ".xlsx";
    }
}
