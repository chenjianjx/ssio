package org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory;

import org.ssio.api.impl.filetypespecific.office.parse.OfficeParseParam;
import org.ssio.api.impl.filetypespecific.office.save.OfficeSaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.model.OfficeWorkbook;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbookFactory;

import java.io.IOException;

public class OfficeWorkbookFactory implements SsWorkbookFactory<OfficeWorkbook> {

    @Override
    public OfficeWorkbook newWorkbookForSave(SaveParam param) {
        return OfficeWorkbook.createNewWorkbook(((OfficeSaveParam) param).getSheetName());
    }

    @Override
    public OfficeWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        OfficeParseParam param = (OfficeParseParam) p;
        return OfficeWorkbook.createFromInput(param.getSpreadsheetInput(), param.getSheetLocator());
    }
}
