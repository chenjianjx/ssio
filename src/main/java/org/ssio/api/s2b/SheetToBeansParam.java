package org.ssio.api.s2b;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;

import java.io.InputStream;

public class SheetToBeansParam<BEAN> {

    public SheetToBeansParam(Class<BEAN> beanClass, InputStream spreadsheetInput, SpreadsheetFileType fileType, SsSheetLocator sheetLocator) {
        this.beanClass = beanClass;
        this.spreadsheetInput = spreadsheetInput;
        this.fileType = fileType;
        this.sheetLocator = sheetLocator;
    }

    /**
     * not null
     */
    private Class<BEAN> beanClass;

    /**
     * not null
     */
    private InputStream spreadsheetInput;

    /**
     * not null
     */
    private SpreadsheetFileType fileType;

    /**
     * which sheet to load the data ?  By default it's the sheet at index 0
     */
    private SsSheetLocator sheetLocator;


    public SsSheetLocator getSheetLocator() {
        return sheetLocator;
    }

    public Class<BEAN> getBeanClass() {
        return beanClass;
    }

    public InputStream getSpreadsheetInput() {
        return spreadsheetInput;
    }


    public SpreadsheetFileType getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
