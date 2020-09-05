package org.ssio.spi.clientexternal.filetypspecific.csv.save;

import org.ssio.spi.clientexternal.filetypspecific.SsBuiltInFileTypes;
import org.ssio.api.external.save.DatumError;
import org.ssio.api.external.save.SaveParam;

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
    protected CsvSaveParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
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

    @Override
    public String getSpreadsheetFileType() {
        return SsBuiltInFileTypes.CSV;
    }
}
