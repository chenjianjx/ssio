package org.ssio.integrationtest.office.cases;

import org.ssio.api.interfaces.office.save.OfficeSaveParamBuilder;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.integrationtest.cases.BeansSaveITCaseBase;
import org.ssio.integrationtest.office.support.OfficeITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public class BeansSaveToOfficeITCase extends BeansSaveITCaseBase {

    @Override
    protected SaveParamBuilder newSaveParamBuilder() {
        return new OfficeSaveParamBuilder().setSheetName("first sheet");
    }

    @Override
    protected String getSpreadsheetFileExtension() {
        return OfficeITHelper.getSpreadsheetFileExtension();
    }

    @Override
    protected WorkbookFactory getWorkbookFactory() {
        return OfficeITHelper.getWorkbookFactory();
    }
}
