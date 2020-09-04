package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.io.IOException;

/**
 * create a workbook object from input , to parse it into beans
 */
public interface WorkbookForSheetToBeansFactory<P extends SheetToBeansParam, W extends SsWorkbook> {

    W load(P param) throws IOException;
}
