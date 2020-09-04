package org.ssio.api.interfaces.b2s;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public abstract class BeansToSheetParam<BEAN> {

    static final String DEFAULT_DATUM_ERR_PLACEHOLDER = "!!ERROR!!";

    public static Function<DatumError, String> DEFAULT_DATUM_ERR_DISPLAY_FUNCTION = (datumError) -> DEFAULT_DATUM_ERR_PLACEHOLDER;

    public static Function<DatumError, String> DATUM_ERR_BLANK_DISPLAY_FUNCTION = (datumError) -> "";

    /**
     * show the stacktrace in the cells. Can be used in troubleshooting situations
     */
    public static Function<DatumError, String> DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION = (datumError) -> {
        return datumError.getStackTrace();
    };


    private Collection<BEAN> beans;


    private Class<BEAN> beanClass;

    private OutputStream outputTarget;


    private boolean createHeader;


    private boolean stillSaveIfDataError;


    private Function<DatumError, String> datumErrDisplayFunction;


    protected BeansToSheetParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        this.beans = beans;
        this.beanClass = beanClass;
        this.outputTarget = outputTarget;
        this.createHeader = createHeader;
        this.stillSaveIfDataError = stillSaveIfDataError;
        this.datumErrDisplayFunction = datumErrDisplayFunction;
    }


    public Collection<BEAN> getBeans() {
        return beans;
    }

    public Class<BEAN> getBeanClass() {
        return beanClass;
    }

    public OutputStream getOutputTarget() {
        return outputTarget;
    }


    public boolean isStillSaveIfDataError() {
        return stillSaveIfDataError;
    }

    public Function<DatumError, String> getDatumErrDisplayFunction() {
        return datumErrDisplayFunction;
    }

    public boolean isCreateHeader() {
        return createHeader;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}