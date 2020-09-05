package org.ssio.spi.clientexternal.filetypespecific.csv.save;

import org.ssio.api.external.save.DatumError;
import org.ssio.api.external.save.SaveParamBuilder;
import org.ssio.spi.clientexternal.filetypespecific.csv.CsvConstants;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CsvSaveParamBuilder<BEAN> extends SaveParamBuilder<BEAN, CsvSaveParamBuilder<BEAN>> {

    private String outputCharset;
    private char cellSeparator = CsvConstants.DEFAULT_CSV_CELL_SEPARATOR;

    /**
     * required for csv, ignored by office-like spreadsheet
     *
     * @return
     */
    public CsvSaveParamBuilder<BEAN> setOutputCharset(String outputCharset) {
        this.outputCharset = outputCharset;
        return this;
    }

    /**
     * Only used for CSV.  Default is ","
     */
    public CsvSaveParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        if (outputCharset == null) {
            errors.add("For CSV output the outputCharset is required");
        }
    }

    @Override
    protected CsvSaveParam<BEAN> fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        return new CsvSaveParam<>(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction, cellSeparator, outputCharset);
    }
}
