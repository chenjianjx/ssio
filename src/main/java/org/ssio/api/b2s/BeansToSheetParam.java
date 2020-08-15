package org.ssio.api.b2s;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;

import java.io.OutputStream;
import java.util.Collection;

public class BeansToSheetParam<BEAN> {

    private static final String DEFAULT_DATUM_ERR_PLACEHOLDER = "!!ERROR!!";

    /**
     * Please use the builder to create an instance
     */
    protected BeansToSheetParam(Collection<BEAN> beans, Class<BEAN> beanClass,
                                OutputStream outputTarget, SpreadsheetFileType fileType,
                                String sheetName, boolean stillSaveIfDataError,
                                String datumErrPlaceholder) {
        this.beans = beans;
        this.beanClass = beanClass;
        this.outputTarget = outputTarget;
        this.fileType = fileType;
        this.sheetName = sheetName;
        this.stillSaveIfDataError = stillSaveIfDataError;
        this.datumErrPlaceholder = datumErrPlaceholder;
    }

    /**
     * not nullable
     */
    private Collection<BEAN> beans;

    /**
     * not nullable
     */
    private Class<BEAN> beanClass;
    /**
     * not nullable
     */
    private OutputStream outputTarget;
    /**
     * not nullable
     */
    private SpreadsheetFileType fileType;

    /**
     * nullable.
     * will be ignored by csv
     */
    private String sheetName;


    /**
     * output the sheet if there are data error?
     * default true
     */
    private boolean stillSaveIfDataError = true;

    /**
     * if some datum is wrong, write this place holder to the cell.
     * default is {@link #DEFAULT_DATUM_ERR_PLACEHOLDER}
     */
    private String datumErrPlaceholder;


    public Collection<BEAN> getBeans() {
        return beans;
    }

    public Class<BEAN> getBeanClass() {
        return beanClass;
    }

    public OutputStream getOutputTarget() {
        return outputTarget;
    }

    public SpreadsheetFileType getFileType() {
        return fileType;
    }

    public String getSheetName() {
        return sheetName;
    }

    public boolean isStillSaveIfDataError() {
        return stillSaveIfDataError;
    }

    public String getDatumErrPlaceholder() {
        if (StringUtils.isBlank(datumErrPlaceholder)) {
            datumErrPlaceholder = DEFAULT_DATUM_ERR_PLACEHOLDER;
        }
        return datumErrPlaceholder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}