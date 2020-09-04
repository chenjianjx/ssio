package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

/**
 * create a new workbook to save beans
 *
 *
 */
public interface WorkbookToSaveFactory {
    SsWorkbook newWorkbook(SaveParam param);
}
