package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

public interface SsWorkbookFactoryRegistry {

    <P extends SaveParam, W extends SsWorkbook> void registerWorkbookToSaveFactory(Class<P> saveParamClass, WorkbookToSaveFactory<P, W> factory);

    <P extends SaveParam, W extends SsWorkbook> WorkbookToSaveFactory<P, W> getWorkbookToSaveFactory(Class<P> saveParamClass);

    <P extends ParseParam, W extends SsWorkbook> void registerWorkbookToParseFactory(Class<P> parseParamClass, WorkbookToParseFactory<P, W> factory);

    <P extends ParseParam, W extends SsWorkbook> WorkbookToParseFactory<P, W> getWorkbookToParseFactory(Class<P> parseParamClass);
}
