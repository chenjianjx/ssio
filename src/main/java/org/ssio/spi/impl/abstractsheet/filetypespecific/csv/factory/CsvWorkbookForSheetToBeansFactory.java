package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory;

import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookForSheetToBeansFactory;

import java.io.IOException;

/**
 * create a workbook object from input
 */
public class CsvWorkbookForSheetToBeansFactory implements WorkbookForSheetToBeansFactory<SheetToBeansParam, CsvWorkbook> {


    @Override
    public CsvWorkbook load(SheetToBeansParam param) throws IOException {
        return null;
    }
}
