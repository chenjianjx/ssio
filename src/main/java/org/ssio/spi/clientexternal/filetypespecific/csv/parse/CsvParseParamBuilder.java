package org.ssio.spi.clientexternal.filetypespecific.csv.parse;

import org.ssio.api.external.parse.ParseParamBuilder;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.spi.clientexternal.filetypespecific.csv.CsvConstants;

import java.io.InputStream;
import java.util.List;

public class CsvParseParamBuilder<BEAN> extends ParseParamBuilder<BEAN, CsvParseParamBuilder<BEAN>> {
    private String inputCharset;
    private char cellSeparator = CsvConstants.DEFAULT_CSV_CELL_SEPARATOR;


    public CsvParseParamBuilder<BEAN> setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
        return this;
    }

    /**
     * Default is ","
     */
    public CsvParseParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    @Override
    protected CsvParseParam fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
        return new CsvParseParam(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader, inputCharset, cellSeparator);
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        if (inputCharset == null) {
            errors.add("For CSV input the inputCharset is required");
        }
    }
}
