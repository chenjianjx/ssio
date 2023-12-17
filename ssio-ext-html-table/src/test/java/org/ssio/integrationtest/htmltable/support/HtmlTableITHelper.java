package org.ssio.integrationtest.htmltable.support;

import org.ssio.spi.impl.htmltable.factory.HtmlTableWorkbookFactory;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class HtmlTableITHelper {

    public static String getSpreadsheetFileExtension() {
        return "html";
    }

    public static WorkbookFactory getWorkbookFactory() {
        return HtmlTableWorkbookFactory.defaultInstance();
    }

}