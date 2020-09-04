package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

/**
 * create a new workbook to save beans
 *
 * @param <W>
 */
public interface WorkbookForBeansToSheetFactory<P extends BeansToSheetParam, W extends SsWorkbook> {
    W newWorkbook(P param);
}
