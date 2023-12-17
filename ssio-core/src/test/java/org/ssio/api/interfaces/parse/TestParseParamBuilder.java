package org.ssio.api.interfaces.parse;

import java.io.InputStream;
import java.util.List;

public class TestParseParamBuilder<BEAN> extends ParseParamBuilder<BEAN, TestParseParamBuilder<BEAN>> {

    @Override
    protected TestParseParam fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
        return new TestParseParam(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
    }
}
