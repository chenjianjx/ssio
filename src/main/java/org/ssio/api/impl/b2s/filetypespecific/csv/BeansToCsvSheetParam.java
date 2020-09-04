package org.ssio.api.impl.b2s.filetypespecific.csv;

import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.b2s.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class BeansToCsvSheetParam<BEAN> extends BeansToSheetParam<BEAN> {

    private char cellSeparator;

    private String outputCharset;

    /**
     * Please use the builder to create an instance
     *
     */
    public BeansToCsvSheetParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
                                boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction,
                                char cellSeparator, String outputCharset) {
        super(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        this.cellSeparator = cellSeparator;
        this.outputCharset = outputCharset;
    }

    public char getCellSeparator() {
        return cellSeparator;
    }

    public String getOutputCharset() {
        return outputCharset;
    }
}
