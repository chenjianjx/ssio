package org.ssio.integrationtest.htmltable.cases;

import org.ssio.api.interfaces.htmltable.parse.HtmlTableParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.integrationtest.cases.SheetParseITCaseBase;
import org.ssio.integrationtest.htmltable.support.HtmlTableITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class SheetParseFromHtmlTableITCase extends SheetParseITCaseBase {

    @Override
    protected ParseParamBuilder newParseParamBuilder() {
        return new HtmlTableParseParamBuilder().setInputCharset("utf8");
    }

    @Override
    public String getSpreadsheetFileExtension() {
        return HtmlTableITHelper.getSpreadsheetFileExtension();
    }

    @Override
    public WorkbookFactory getWorkbookFactory() {
        return HtmlTableITHelper.getWorkbookFactory();
    }

}
