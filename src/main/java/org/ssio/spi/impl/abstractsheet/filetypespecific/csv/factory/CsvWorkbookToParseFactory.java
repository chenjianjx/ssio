package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToParseFactory;

import java.io.IOException;

/**
 * create a workbook object from input
 */
public class CsvWorkbookToParseFactory implements WorkbookToParseFactory<ParseParam, CsvWorkbook> {


    @Override
    public CsvWorkbook load(ParseParam param) throws IOException {
        return null;
    }
}
