package org.ssio.api.impl;

import org.ssio.api.impl.parse.SheetParser;
import org.ssio.api.impl.save.BeansSaver;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;


public class SsioManagerImpl implements SsioManager {

    private BeansSaver beansSaver;

    private SheetParser sheetParser;


    public SsioManagerImpl(WorkbookFactory workbookFactory) {
        beansSaver = new BeansSaver(workbookFactory);
        sheetParser = new SheetParser(workbookFactory);
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
