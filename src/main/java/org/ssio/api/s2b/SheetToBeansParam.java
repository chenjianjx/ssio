package org.ssio.api.s2b;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;

import java.io.InputStream;

public class SheetToBeansParam<BEAN> {

    public SheetToBeansParam(Class<BEAN> beanClass, InputStream spreadsheetInput, String inputCharset, SpreadsheetFileType fileType, char cellSeparator, SsSheetLocator sheetLocator) {
        this.beanClass = beanClass;
        this.spreadsheetInput = spreadsheetInput;
        this.inputCharset = inputCharset;
        this.fileType = fileType;
        this.cellSeparator = cellSeparator;
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
     * Required for CSV input.  Ignored by office(Excel) input
     */
    private String inputCharset;

    /**
     * Only used for CSV.  Default is ","
     */
    private char cellSeparator;

    /**
     * not null
     */
    private SpreadsheetFileType fileType;

    /**
     * which sheet to load the data ?  By default it's the sheet at index 0
     */
    private SsSheetLocator sheetLocator;


    public char getCellSeparator() {
        return cellSeparator;
    }

    public SsSheetLocator getSheetLocator() {
        return sheetLocator;
    }

    public Class<BEAN> getBeanClass() {
        return beanClass;
    }

    public InputStream getSpreadsheetInput() {
        return spreadsheetInput;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public SpreadsheetFileType getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
