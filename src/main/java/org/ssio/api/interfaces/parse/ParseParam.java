package org.ssio.api.interfaces.parse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes;
import org.ssio.api.impl.common.sheetlocate.SsSheetLocator;

import java.io.InputStream;

public class ParseParam<BEAN> {

    public ParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, String inputCharset, SsBuiltInFileTypes fileType, char cellSeparator, SsSheetLocator sheetLocator, boolean sheetHasHeader) {
        this.beanClass = beanClass;
        this.propFromColumnMappingMode = propFromColumnMappingMode;
        this.spreadsheetInput = spreadsheetInput;
        this.inputCharset = inputCharset;
        this.fileType = fileType;
        this.cellSeparator = cellSeparator;
        this.sheetLocator = sheetLocator;
        this.sheetHasHeader = sheetHasHeader;
    }

    private Class<BEAN> beanClass;


    private PropFromColumnMappingMode propFromColumnMappingMode;


    private InputStream spreadsheetInput;


    private String inputCharset;


    private char cellSeparator;


    private SsBuiltInFileTypes fileType;


    private SsSheetLocator sheetLocator;


    private boolean sheetHasHeader;


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

    public SsBuiltInFileTypes getFileType() {
        return fileType;
    }

    public PropFromColumnMappingMode getPropFromColumnMappingMode() {
        return propFromColumnMappingMode;
    }

    public boolean isSheetHasHeader() {
        return sheetHasHeader;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
