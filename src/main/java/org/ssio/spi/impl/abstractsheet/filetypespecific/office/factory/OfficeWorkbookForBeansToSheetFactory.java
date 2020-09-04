package org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory;

import org.ssio.api.impl.b2s.filetypespecific.office.BeansToOfficeSheetParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.model.OfficeWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookForBeansToSheetFactory;

public class OfficeWorkbookForBeansToSheetFactory implements WorkbookForBeansToSheetFactory<BeansToOfficeSheetParam, OfficeWorkbook> {

    @Override
    public OfficeWorkbook newWorkbook(BeansToOfficeSheetParam param) {
        return OfficeWorkbook.createNewWorkbook();
    }
}
