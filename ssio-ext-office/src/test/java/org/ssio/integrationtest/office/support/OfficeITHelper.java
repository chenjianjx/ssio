package org.ssio.integrationtest.office.support;

import org.ssio.spi.impl.office.factory.OfficeWorkbookFactory;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class OfficeITHelper {

    public static String getSpreadsheetFileExtension() {
        return "xlsx";
    }


    public static WorkbookFactory getWorkbookFactory() {
        return new OfficeWorkbookFactory();
    }
}
