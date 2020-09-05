package org.ssio.spi.internal.abstractsheet.filetypespecific.csv.factory;

import org.ssio.spi.clientexternal.filetypspecific.csv.parse.CsvParseParam;
import org.ssio.spi.clientexternal.filetypspecific.csv.save.CsvSaveParam;
import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.save.SaveParam;
import org.ssio.spi.internal.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class CsvWorkbookFactory implements SsWorkbookFactory<CsvWorkbook> {


    @Override
    public CsvWorkbook newWorkbookForSave(SaveParam p) {
        CsvSaveParam param = (CsvSaveParam) p;
        return CsvWorkbook.createNewWorkbook(param.getCellSeparator(), param.getOutputCharset());
    }

    @Override
    public CsvWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        CsvParseParam param = (CsvParseParam) p;
        return CsvWorkbook.createFromInput(new InputStreamReader(param.getSpreadsheetInput(), param.getInputCharset()), param.getCellSeparator());
    }
}