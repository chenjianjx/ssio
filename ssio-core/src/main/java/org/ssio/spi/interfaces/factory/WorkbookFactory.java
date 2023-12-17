package org.ssio.spi.interfaces.factory;

import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;
import org.ssio.spi.interfaces.model.write.WritableWorkbook;

import java.io.IOException;

/**
 * The factory to create an abstract workbook
 * An implementation of this class will serve as the entry of the extension's implementation
 */
public interface WorkbookFactory<RW extends ReadableWorkbook, WW extends WritableWorkbook> {

    /**
     * create a new workbook for beans save
     */
    WW newWorkbookForSave(SaveParam param);


    /**
     * create a workbook object from input , to parse it into beans
     */
    RW loadWorkbookToParse(ParseParam param) throws IOException;

}
