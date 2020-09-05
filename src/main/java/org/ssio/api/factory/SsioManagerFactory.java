package org.ssio.api.factory;

import org.ssio.api.external.SsioManager;
import org.ssio.api.internal.SsioManagerImpl;
import org.ssio.spi.clientexternal.spiregistry.SsWorkbookFactoryRegistry;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;

import java.util.Collections;
import java.util.Map;

/**
 * If you have an IoC framework, don't use this class. Instead, configure   {@link SsioManagerImpl} as a component
 */
public class SsioManagerFactory {

    public static SsioManager newInstance() {
        return newInstance(Collections.emptyMap());
    }

    /**
     * Use this one if you have workbooks outside {@link org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes}
     *
     * @param extraWorkbookFactories
     * @return
     */
    public static SsioManager newInstance(Map<String, SsWorkbookFactory> extraWorkbookFactories) {
        SsWorkbookFactoryRegistry workbookFactoryRegistry = new SsWorkbookFactoryRegistry();
        extraWorkbookFactories.forEach((fileType, factory) -> workbookFactoryRegistry.register(fileType, factory));
        return new SsioManagerImpl(workbookFactoryRegistry);
    }


}
