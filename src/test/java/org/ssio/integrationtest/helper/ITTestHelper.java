package org.ssio.integrationtest.helper;

import org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes;

public class ITTestHelper {
    public static String decideTargetFileExtension(String spreadsheetFileType) {
        return spreadsheetFileType.equals(SsBuiltInFileTypes.CSV) ? ".csv" : ".xlsx";
    }
}
