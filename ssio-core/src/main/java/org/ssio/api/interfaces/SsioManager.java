package org.ssio.api.interfaces;

import org.ssio.api.impl.SsioManagerImpl;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;

/**
 * The facade - for every extension you are going to use,  create such a manager
 */
public interface SsioManager {

    /**
     * If you have an IoC framework, don't use this method. Instead, configure {@link SsioManagerImpl} as a component
     * @param workbookFactory Used to create a workbook for the extension you are using
     
     */
    static SsioManager newInstance(WorkbookFactory workbookFactory) {
        return new SsioManagerImpl(workbookFactory);
    }

    /**
     * save beans to sheet
     *
     * @param param Please use {@link SaveParamBuilder} to create the param
     */
    <BEAN> SaveResult save(SaveParam<BEAN> param) throws IOException;


    /**
     * parse sheet into beans
     *
     * @param param Please use {@link ParseParam} to create the param
     
     */
    <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException;
}
