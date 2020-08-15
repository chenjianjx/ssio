package org.ssio.api.abstractsheet;

import org.ssio.api.b2s.DatumError;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * file-type-independent sheet
 */
public interface SsSheet {

    void createHeaderRow(Map<String, String> headerMap);

    <BEAN> void createDataRow(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, Function<DatumError, String> datumErrDisplayFunction, List<DatumError> datumErrors);
}
