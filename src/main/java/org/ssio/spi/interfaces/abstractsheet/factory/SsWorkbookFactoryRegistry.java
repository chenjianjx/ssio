package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbookFactory;

public interface SsWorkbookFactoryRegistry {

    /**
     * @param spreadSheetFileType Note: Some built-in types are defined on {@link org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes}
     */
    void registerWorkbook(String spreadSheetFileType, SsWorkbookFactory factory);

    SsWorkbookFactory getWorkbookFactory(String spreadSheetFileType);


}
