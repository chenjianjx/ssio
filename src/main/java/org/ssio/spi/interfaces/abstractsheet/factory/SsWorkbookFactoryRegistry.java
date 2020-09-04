package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;

public interface SsWorkbookFactoryRegistry {

    void registerWorkbookToSaveFactory(Class<? extends SaveParam> saveParamClass, WorkbookToSaveFactory factory);

    WorkbookToSaveFactory getWorkbookToSaveFactory(Class<? extends SaveParam> saveParamClass);

    void registerWorkbookToParseFactory(Class<ParseParam> parseParamClass, WorkbookToParseFactory factory);

    WorkbookToParseFactory getWorkbookToParseFactory(Class<ParseParam> parseParamClass);
}
