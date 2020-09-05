package org.ssio.api.interfaces;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;

import java.io.IOException;

public interface SsioManager {

    /**
     * beans to sheet
     *
     * @param param Please use {@link SaveParamBuilder} to create the param
     */
    <BEAN> SaveResult save(SaveParam<BEAN> param) throws IOException;


    /**
     * sheet to beans
     *
     * @param param Please use {@link ParseParam} to create the param
     * @return
     */
    <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException;
}
