package org.ssio.api.internal;

import org.ssio.api.external.SsioManager;
import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.ParseResult;
import org.ssio.api.external.save.SaveParam;
import org.ssio.api.external.save.SaveResult;
import org.ssio.api.internal.parse.SheetParser;
import org.ssio.api.internal.save.BeansSaver;
import org.ssio.spi.clientexternal.spiregistry.SsWorkbookFactoryRegistry;

import java.io.IOException;


public class SsioManagerImpl implements SsioManager {

    private BeansSaver beansSaver;

    private SheetParser sheetParser;

    private SsWorkbookFactoryRegistry workbookFactoryRegistry;

    public SsioManagerImpl(SsWorkbookFactoryRegistry workbookFactoryRegistry) {

        this.workbookFactoryRegistry = workbookFactoryRegistry;

        beansSaver = new BeansSaver(workbookFactoryRegistry);
        sheetParser = new SheetParser(workbookFactoryRegistry);
    }

    @Override
    public <BEAN> SaveResult save(SaveParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }
        return beansSaver.doSave(param);
    }


    @Override
    public <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        return sheetParser.doWork(param);
    }

}
