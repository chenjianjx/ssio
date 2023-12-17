package org.ssio.api.interfaces.parse;

import java.io.InputStream;

public class TestParseParam<BEAN> extends ParseParam<BEAN> {

    public TestParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
    }

}
