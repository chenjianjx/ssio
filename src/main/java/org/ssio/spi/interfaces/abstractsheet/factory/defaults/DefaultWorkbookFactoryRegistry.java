package org.ssio.spi.interfaces.abstractsheet.factory.defaults;

import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.spi.interfaces.abstractsheet.factory.SsWorkbookFactoryRegistry;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToParseFactory;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToSaveFactory;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * a plain implementation
 */
public class DefaultWorkbookFactoryRegistry implements SsWorkbookFactoryRegistry {

    private Map<Class<? extends SaveParam>, WorkbookToSaveFactory<? extends SaveParam, ?>> saveFactories = new LinkedHashMap<>();

    private Map<Class<? extends ParseParam>, WorkbookToParseFactory<? extends ParseParam, ?>> parseFactories = new LinkedHashMap<>();

    @Override
    public <P extends SaveParam, W extends SsWorkbook> void registerWorkbookToSaveFactory(Class<P> saveParamClass, WorkbookToSaveFactory<P, W> factory) {
        saveFactories.put(saveParamClass, factory);
    }

    @Override
    public <P extends SaveParam, W extends SsWorkbook> WorkbookToSaveFactory<P, W> getWorkbookToSaveFactory(Class<P> saveParamClass) {
        return (WorkbookToSaveFactory<P, W>) saveFactories.get(saveParamClass);
    }

    @Override
    public <P extends ParseParam, W extends SsWorkbook> void registerWorkbookToParseFactory(Class<P> parseParamClass, WorkbookToParseFactory<P, W> factory) {
        parseFactories.put(parseParamClass, factory);
    }

    @Override
    public <P extends ParseParam, W extends SsWorkbook> WorkbookToParseFactory<P, W> getWorkbookToParseFactory(Class<P> parseParamClass) {
        return (WorkbookToParseFactory<P, W>) parseFactories.get(parseParamClass);
    }
}
