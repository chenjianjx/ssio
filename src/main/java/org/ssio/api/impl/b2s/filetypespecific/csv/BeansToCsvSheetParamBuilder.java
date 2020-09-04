package org.ssio.api.impl.b2s.filetypespecific.csv;

import org.ssio.api.interfaces.SsioApiConstants;
import org.ssio.api.interfaces.b2s.BeansToSheetParamBuilder;
import org.ssio.api.interfaces.b2s.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class BeansToCsvSheetParamBuilder<BEAN> extends BeansToSheetParamBuilder<BEAN, BeansToCsvSheetParam<BEAN>, BeansToCsvSheetParamBuilder> {

    private String outputCharset;
    private char cellSeparator = SsioApiConstants.DEFAULT_CSV_CELL_SEPARATOR;

    /**
     * required for csv, ignored by office-like spreadsheet
     *
     * @return
     */
    public BeansToCsvSheetParamBuilder<BEAN> setOutputCharset(String outputCharset) {
        this.outputCharset = outputCharset;
        return this;
    }

    /**
     * Only used for CSV.  Default is ","
     */
    public BeansToCsvSheetParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
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
    protected BeansToCsvSheetParam fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        return new BeansToCsvSheetParam(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction, cellSeparator, outputCharset);
    }
}
