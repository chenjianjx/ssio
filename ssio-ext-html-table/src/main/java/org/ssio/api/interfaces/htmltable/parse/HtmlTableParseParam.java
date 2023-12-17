package org.ssio.api.interfaces.htmltable.parse;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;

import java.io.InputStream;

public class HtmlTableParseParam<BEAN> extends ParseParam<BEAN> {

    private String inputCharset;


    /**
     * Please the builder to build it
     */
    protected HtmlTableParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode,
                                  InputStream spreadsheetInput, boolean sheetHasHeader,
                                  String inputCharset) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        this.inputCharset = inputCharset;

    }

    public String getInputCharset() {
        return inputCharset;
    }
}
