package org.ssio.integrationtest.cases;

import org.ssio.api.interfaces.SsioManager;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

public abstract class SaveParseITCaseBase {
    static {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    protected SsioManager manager = createManager();

    private SsioManager createManager() {
        return SsioManager.newInstance(getWorkbookFactory());
    }

    protected abstract String getSpreadsheetFileExtension();

    protected abstract WorkbookFactory getWorkbookFactory();


}