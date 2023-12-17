package org.ssio.api.interfaces.csv.parse;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;

import java.io.InputStream;

public class CsvParseParam<BEAN> extends ParseParam<BEAN> {

    private String inputCharset;

    private char cellSeparator;

    /**
     * Please use the builder to build it
     */
    protected CsvParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader,
                            String inputCharset, char cellSeparator) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        this.inputCharset = inputCharset;
        this.cellSeparator = cellSeparator;
    }

    public char getCellSeparator() {
        return cellSeparator;
    }

    public String getInputCharset() {
        return inputCharset;
    }
}
