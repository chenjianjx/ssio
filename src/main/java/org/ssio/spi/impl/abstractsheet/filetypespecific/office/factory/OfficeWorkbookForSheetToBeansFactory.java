package org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory;

import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.model.OfficeWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookForSheetToBeansFactory;

import java.io.IOException;

/**
 * create a workbook object from input
 */
public class OfficeWorkbookForSheetToBeansFactory implements WorkbookForSheetToBeansFactory<SheetToBeansParam, OfficeWorkbook> {


    @Override
    public OfficeWorkbook load(SheetToBeansParam param) throws IOException {
        return null;
    }
}
