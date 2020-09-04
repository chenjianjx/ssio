package org.ssio.api.impl;

import org.ssio.api.impl.save.BeansSaver;
import org.ssio.api.impl.parse.SheetParser;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;

import java.io.IOException;

/**
 * The facade.
 * It's state-less, hence thread-safe
 */
public class SsioManagerImpl implements SsioManager {

    private BeansSaver beansSaver;

    private SheetParser sheetParser;

    @Override
    public <BEAN> SaveResult save(SaveParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        return beansSaver.doWork(param);
    }


    @Override
    public <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }
        SheetParser worker = new SheetParser();
        return worker.doWork(param);
    }
}
