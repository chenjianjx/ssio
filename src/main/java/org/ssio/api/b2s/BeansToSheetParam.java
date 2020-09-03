package org.ssio.api.b2s;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class BeansToSheetParam<BEAN> {

    static final String DEFAULT_DATUM_ERR_PLACEHOLDER = "!!ERROR!!";

    public static Function<DatumError, String> DEFAULT_DATUM_ERR_DISPLAY_FUNCTION = (datumError) -> DEFAULT_DATUM_ERR_PLACEHOLDER;

    public static Function<DatumError, String> DATUM_ERR_BLANK_DISPLAY_FUNCTION = (datumError) -> "";

    /**
     * show the stacktrace in the cells. Can be used in troubleshooting situations
     */
    public static Function<DatumError, String> DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION = (datumError) -> {
        return datumError.getStackTrace();
    };

    /**
     * Please use the builder to create an instance
     *
     * @param beans
     * @param beanClass
     * @param outputTarget
     * @param fileType
     * @param cellSeparator
     * @param createHeader
     * @param sheetName
     * @param stillSaveIfDataError
     * @param datumErrDisplayFunction
     */
    public BeansToSheetParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, SpreadsheetFileType fileType, char cellSeparator, boolean createHeader,
                             String sheetName, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        this.beans = beans;
        this.beanClass = beanClass;
        this.outputTarget = outputTarget;
        this.fileType = fileType;
        this.cellSeparator = cellSeparator;
        this.createHeader = createHeader;
        this.sheetName = sheetName;
        this.stillSaveIfDataError = stillSaveIfDataError;
        this.datumErrDisplayFunction = datumErrDisplayFunction;
    }


    private Collection<BEAN> beans;


    private Class<BEAN> beanClass;

    private OutputStream outputTarget;

    private SpreadsheetFileType fileType;


    private char cellSeparator;


    private boolean createHeader;


    private String sheetName;


    private boolean stillSaveIfDataError;


    private Function<DatumError, String> datumErrDisplayFunction;


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

    public Function<DatumError, String> getDatumErrDisplayFunction() {
        return datumErrDisplayFunction;
    }

    public char getCellSeparator() {
        return cellSeparator;
    }

    public boolean isCreateHeader() {
        return createHeader;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}