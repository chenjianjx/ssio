package org.ssio.api.abstractsheet;

import java.io.IOException;
import java.io.OutputStream;

/**
 * file-type-independent workbook
 */
public interface SsWorkbook {
    SsSheet createSheet(String sheetName);

    void write(OutputStream outputTarget) throws IOException;
}