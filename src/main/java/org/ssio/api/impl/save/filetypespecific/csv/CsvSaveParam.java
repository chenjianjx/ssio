package org.ssio.api.impl.save.filetypespecific.csv;

import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class CsvSaveParam<BEAN> extends SaveParam<BEAN> {

    private char cellSeparator;

    private String outputCharset;

    /**
     * Please use the builder to create an instance
     *
     */
    public CsvSaveParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
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
