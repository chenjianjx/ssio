package org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.model.OfficeWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToParseFactory;

import java.io.IOException;

/**
 * create a workbook object from input
 */
public class OfficeWorkbookToParseFactory implements WorkbookToParseFactory<ParseParam, OfficeWorkbook> {


    @Override
    public OfficeWorkbook load(ParseParam param) throws IOException {
        return null;
    }
}
