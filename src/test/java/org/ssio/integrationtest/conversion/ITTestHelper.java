package org.ssio.integrationtest.conversion;

import org.ssio.spi.clientexternal.filetypspecific.SsBuiltInFileTypes;

public class ITTestHelper {
    public static String decideTargetFileExtension(String spreadsheetFileType) {
        return spreadsheetFileType.equals( SsBuiltInFileTypes.CSV) ? ".csv" : ".xlsx";
    }
}
