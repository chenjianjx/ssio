package org.ssio.spi.interfaces.abstractsheet.model;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;

import java.io.IOException;

public interface SsWorkbookFactory<W extends SsWorkbook> {

    /**
     * create a new workbook for beans save
     */
    W newWorkbookForSave(SaveParam param);


    /**
     * create a workbook object from input , to parse it into beans
     */
    W loadWorkbookToParse(ParseParam param) throws IOException;

}
