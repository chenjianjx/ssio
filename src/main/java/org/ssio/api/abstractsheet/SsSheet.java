package org.ssio.api.abstractsheet;

import org.ssio.api.b2s.DatumError;

import java.util.List;
import java.util.Map;

/**
 * file-type-independent sheet
 */
public interface SsSheet {

    SsRow createHeaderRow(Map<String, String> headerMap);

    <BEAN> SsRow createDataRow(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, String datumErrPlaceholder, List<DatumError> datumErrors);
}
