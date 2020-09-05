package org.ssio.api.external.parse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.InputStream;

public abstract class ParseParam<BEAN> {

    public ParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
        this.beanClass = beanClass;
        this.propFromColumnMappingMode = propFromColumnMappingMode;
        this.spreadsheetInput = spreadsheetInput;
        this.sheetHasHeader = sheetHasHeader;
    }

    private Class<BEAN> beanClass;


    private PropFromColumnMappingMode propFromColumnMappingMode;


    private InputStream spreadsheetInput;


    private boolean sheetHasHeader;


    public Class<BEAN> getBeanClass() {
        return beanClass;
    }

    public InputStream getSpreadsheetInput() {
        return spreadsheetInput;
    }


    public PropFromColumnMappingMode getPropFromColumnMappingMode() {
        return propFromColumnMappingMode;
    }

    public boolean isSheetHasHeader() {
        return sheetHasHeader;
    }

    public abstract String getSpreadsheetFileType();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
