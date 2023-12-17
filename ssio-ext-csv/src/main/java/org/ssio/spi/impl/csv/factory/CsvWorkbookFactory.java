package org.ssio.spi.impl.csv.factory;

import org.ssio.api.interfaces.csv.parse.CsvParseParam;
import org.ssio.api.interfaces.csv.save.CsvSaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.impl.csv.model.read.CsvReadableWorkbook;
import org.ssio.spi.impl.csv.model.write.CsvWritableWorkbook;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class CsvWorkbookFactory implements WorkbookFactory<CsvReadableWorkbook, CsvWritableWorkbook> {

    private static CsvWorkbookFactory DEFAULT_INSTANCE = new CsvWorkbookFactory();

    public static WorkbookFactory defaultInstance() {
        return DEFAULT_INSTANCE;
    }


    @Override
    public CsvWritableWorkbook newWorkbookForSave(SaveParam p) {
        CsvSaveParam param = (CsvSaveParam) p;
        return CsvWritableWorkbook.createNewWorkbook(param.getCellSeparator(), param.getOutputCharset());
    }

    @Override
    public CsvReadableWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        CsvParseParam param = (CsvParseParam) p;
        return CsvReadableWorkbook.newInstanceFromInput(new InputStreamReader(param.getSpreadsheetInput(), param.getInputCharset()), param.getCellSeparator());
    }
}
