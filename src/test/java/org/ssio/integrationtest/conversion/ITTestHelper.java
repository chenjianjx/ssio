package org.ssio.integrationtest.conversion;

import org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes;

public class ITTestHelper {
    public static String decideTargetFileExtension(SsBuiltInFileTypes spreadsheetFileType) {
        return spreadsheetFileType == SsBuiltInFileTypes.CSV ? ".csv" : ".xlsx";
    }
}
