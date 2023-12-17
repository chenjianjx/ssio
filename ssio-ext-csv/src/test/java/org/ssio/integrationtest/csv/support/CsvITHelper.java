package org.ssio.integrationtest.csv.support;

import org.ssio.spi.impl.csv.factory.CsvWorkbookFactory;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class CsvITHelper {

    public static String getSpreadsheetFileExtension() {
        return "csv";
    }

    public static WorkbookFactory getWorkbookFactory() {
        return CsvWorkbookFactory.defaultInstance();
    }

    public static String getSeparatorName(char cellSeparator) {
        if (cellSeparator == ',') {
            return "comma";
        }
        if (cellSeparator == '\t') {
            return "tab";
        }
        throw new UnsupportedOperationException("Don't know the name of this separator: " + cellSeparator);
    }

}