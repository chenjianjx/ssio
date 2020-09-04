package org.ssio.spi.interfaces.abstractsheet.factory.defaults;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.interfaces.abstractsheet.factory.SsWorkbookFactoryRegistry;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToParseFactory;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToSaveFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * a plain implementation
 */
public class DefaultWorkbookFactoryRegistry implements SsWorkbookFactoryRegistry {

    private Map<Class<SaveParam>, WorkbookToSaveFactory> saveFactories = new LinkedHashMap<>();

    private Map<Class<ParseParam>, WorkbookToParseFactory> parseFactories = new LinkedHashMap<>();

    @Override
    public void registerWorkbookToSaveFactory(Class<SaveParam> saveParamClass, WorkbookToSaveFactory factory) {

    }

    @Override
    public WorkbookToSaveFactory getWorkbookToSaveFactory(Class<? extends SaveParam> saveParamClass) {
        return null;
    }

    @Override
    public void registerWorkbookToParseFactory(Class<ParseParam> parseParamClass, WorkbookToParseFactory factory) {

    }

    @Override
    public WorkbookToParseFactory getWorkbookToParseFactory(Class<ParseParam> parseParamClass) {
        return null;
    }

//    @Override
//    public void registerWorkbookToSaveFactory(Class<SaveParam> saveParamClass, WorkbookToSaveFactory factory) {
//        saveFactories.put(saveParamClass, factory);
//    }
//
//    @Override
//    public WorkbookToSaveFactory getWorkbookToSaveFactory(Class<SaveParam> saveParamClass) {
//        return saveFactories.get(saveParamClass);
//    }
//
//    @Override
//    public void registerWorkbookToParseFactory(Class<ParseParam> parseParamClass, WorkbookToParseFactory factory) {
//        parseFactories.put(parseParamClass, factory);
//    }
//
//    @Override
//    public WorkbookToParseFactory getWorkbookToParseFactory(Class<ParseParam> parseParamClass) {
//        return parseFactories.get(parseParamClass);
//    }


}
