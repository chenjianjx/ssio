package org.ssio.api.s2b;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;

import java.io.InputStream;

public class SheetToBeansParam<T> {

    public SheetToBeansParam(Class<T> beanClass, InputStream spreadsheetInput, SpreadsheetFileType fileType, String sheetName, int sheetIndex) {
        this.beanClass = beanClass;
        this.spreadsheetInput = spreadsheetInput;
        this.sheetName = sheetName;
        this.fileType = fileType;
        this.sheetIndex = sheetIndex;
    }

    /**
     * not null
     */
    private Class<T> beanClass;

    /**
     * not null
     */
    private InputStream spreadsheetInput;

    /**
     * not null
     */
    private SpreadsheetFileType fileType;

    /**
     *
     * which sheet to load the data ?  This has higher priority than {@link #sheetIndex}
     * nullable.
     * Will be ignored if input is csv
     */
    private String sheetName;

    /**
     * which sheet to load the data?  default 0
     * not null
     * Will be ignored if input is csv
     */
    private int sheetIndex = 0;


    public Class<T> getBeanClass() {
        return beanClass;
    }

    public InputStream getSpreadsheetInput() {
        return spreadsheetInput;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public SpreadsheetFileType getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
