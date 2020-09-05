package org.ssio.spi.developerexternal.abstractsheet.factory;

import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.save.SaveParam;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

import java.io.IOException;

/**
 * The factory to create an abstract workbook
 * An implementation of this class will serve as the entry of the spi-implementation
 *
 * @param <W>
 */
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
