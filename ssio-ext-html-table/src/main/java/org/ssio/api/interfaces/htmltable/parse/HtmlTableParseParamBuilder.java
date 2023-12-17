package org.ssio.api.interfaces.htmltable.parse;



import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;

import java.io.InputStream;
import java.util.List;

public class HtmlTableParseParamBuilder<BEAN> extends ParseParamBuilder<BEAN, HtmlTableParseParamBuilder<BEAN>> {
    private String inputCharset;


    public HtmlTableParseParamBuilder<BEAN> setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
        return this;
    }


    @Override
    protected HtmlTableParseParam fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode,
                                                        InputStream spreadsheetInput, boolean sheetHasHeader) {
        return new HtmlTableParseParam(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader, inputCharset);
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        if (inputCharset == null) {
            errors.add("For html table the inputCharset is required");
        }
    }
}
