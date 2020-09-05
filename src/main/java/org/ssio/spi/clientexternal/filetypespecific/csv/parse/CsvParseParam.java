package org.ssio.spi.clientexternal.filetypespecific.csv.parse;

import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes;

import java.io.InputStream;

public class CsvParseParam<BEAN> extends ParseParam<BEAN> {

    private String inputCharset;

    private char cellSeparator;

    /**
     * Please use the builder to build it
     */
    protected CsvParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader,
                            String inputCharset, char cellSeparator) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        this.inputCharset = inputCharset;
        this.cellSeparator = cellSeparator;
    }

    public char getCellSeparator() {
        return cellSeparator;
    }

    public String getInputCharset() {
        return inputCharset;
    }


    @Override
    public String getSpreadsheetFileType() {
        return SsBuiltInFileTypes.CSV;
    }
}
