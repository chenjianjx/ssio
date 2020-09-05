package org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory;

import org.ssio.api.impl.filetypespecific.office.save.OfficeSaveParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.model.OfficeWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToSaveFactory;

public class OfficeWorkbookToSaveFactory implements WorkbookToSaveFactory<OfficeSaveParam, OfficeWorkbook> {

    @Override
    public OfficeWorkbook newWorkbook(OfficeSaveParam param) {
        return OfficeWorkbook.createNewWorkbook(param.getSheetName());
    }
}
