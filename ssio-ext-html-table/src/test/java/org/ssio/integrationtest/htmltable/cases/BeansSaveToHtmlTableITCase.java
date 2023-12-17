package org.ssio.integrationtest.htmltable.cases;

import org.ssio.api.interfaces.htmltable.save.HtmlTableSaveParamBuilder;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.integrationtest.cases.BeansSaveITCaseBase;
import org.ssio.integrationtest.htmltable.support.HtmlTableITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class BeansSaveToHtmlTableITCase extends BeansSaveITCaseBase {

    @Override
    protected SaveParamBuilder newSaveParamBuilder() {
        return new HtmlTableSaveParamBuilder().setOutputCharset("utf8");
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
