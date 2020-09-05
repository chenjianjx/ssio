package org.ssio.spi.interfaces.abstractsheet.factory.defaults;

import org.ssio.spi.interfaces.abstractsheet.factory.SsWorkbookFactoryRegistry;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbookFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * a plain implementation
 */
public class DefaultWorkbookFactoryRegistry implements SsWorkbookFactoryRegistry {

    private Map<String, SsWorkbookFactory> factoryMap = new LinkedHashMap<>();

    @Override
    public void register(String spreadSheetFileType, SsWorkbookFactory factory) {
        factoryMap.put(spreadSheetFileType, factory);
    }

    @Override
    public SsWorkbookFactory getFactory(String spreadSheetFileType) {
        return factoryMap.get(spreadSheetFileType);
    }
}
