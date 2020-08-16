package org.ssio.api.abstractsheet;

import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.b2s.DatumError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

/**
 * file-type-independent sheet
 */
public interface SsSheet {

    void createHeaderRow(LinkedHashMap<String, String> headerMap);

    <BEAN> void createDataRow(LinkedHashMap<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, Function<DatumError, String> datumErrDisplayFunction, List<DatumError> datumErrors);

    /**
     *
     * @param rowIndex 0-based
     * @return
     */
    SsRow getRow(int rowIndex);

    int getNumberOfRows();
}
