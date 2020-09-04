package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.util.LinkedHashMap;
import java.util.Map;

public class SsWorkbookFactoryRegistry {

    private Map<Class<? extends SaveParam>, WorkbookToSaveFactory<? extends SaveParam, ?>> workbookToSaveFactories = new LinkedHashMap<>();

    private Map<Class<? extends ParseParam>, WorkbookToParseFactory<? extends ParseParam, ?>> workbookToParseFactories = new LinkedHashMap<>();

    public <P extends SaveParam, W extends SsWorkbook> void registerWorkbookToSaveFactory(Class<P> saveParamClass, WorkbookToSaveFactory<P, W> factory) {
        workbookToSaveFactories.put(saveParamClass, factory);
    }

    public <P extends ParseParam, W extends SsWorkbook> void registerWorkbookToParseFactory(Class<P> parseParamClass, WorkbookToParseFactory<P, W> factory) {
        workbookToParseFactories.put(parseParamClass, factory);
    }
}
