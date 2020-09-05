package org.ssio.spi.internal.filetypespecific.abstractsheet.office.factory;

import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.save.SaveParam;
import org.ssio.spi.clientexternal.filetypespecific.office.parse.OfficeParseParam;
import org.ssio.spi.clientexternal.filetypespecific.office.save.OfficeSaveParam;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.model.OfficeWorkbook;

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
