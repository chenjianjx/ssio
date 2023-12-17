package org.ssio.spi.impl.office.factory;

import org.ssio.api.interfaces.office.parse.OfficeParseParam;
import org.ssio.api.interfaces.office.save.OfficeSaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.impl.office.model.read.OfficeReadableWorkbook;
import org.ssio.spi.impl.office.model.write.OfficeWritableWorkbook;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;

public class OfficeWorkbookFactory implements WorkbookFactory<OfficeReadableWorkbook, OfficeWritableWorkbook> {

    private static OfficeWorkbookFactory DEFAULT_INSTANCE = new OfficeWorkbookFactory();

    public static WorkbookFactory defaultInstance() {
        return DEFAULT_INSTANCE;
    }
    @Override
    public OfficeWritableWorkbook newWorkbookForSave(SaveParam param) {
        return OfficeWritableWorkbook.createNewWorkbook(((OfficeSaveParam) param).getSheetName());
    }

    @Override
    public OfficeReadableWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        OfficeParseParam param = (OfficeParseParam) p;
        return OfficeReadableWorkbook.newInstanceFromInput(param.getSpreadsheetInput(), param.getSheetLocator());
    }
}
