package org.ssio.api.external;

import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.ParseResult;
import org.ssio.api.external.save.SaveParam;
import org.ssio.api.external.save.SaveParamBuilder;
import org.ssio.api.external.save.SaveResult;

import java.io.IOException;

/**
 * The facade
 */
public interface SsioManager {


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
     * @return
     */
    <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException;
}
