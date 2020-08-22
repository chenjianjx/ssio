package org.ssio.api.common.abstractsheet.model;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.ssio.api.b2s.DatumError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.ssio.internal.util.SsioReflectionHelper.getPropertyEnumClassIfEnum;

/**
 * file-type-independent sheet
 */
public interface SsSheet {

    int getNumberOfRows();

    /**
     * @param rowIndex 0-based
     * @return
     */
    SsRow getRow(int rowIndex);

    /**
     * @param rowIndex 0-based
     * @return
     */
    SsRow createNewRow(int rowIndex);


    default SsRow createHeaderRow(LinkedHashMap<String, String> headerMap) {

        SsRow header = this.createNewRow(0);
        int columnIndex = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            String headerText = StringUtils.defaultString(entry.getValue());
            SsCell cell = header.createCell(columnIndex);
            cell.writeValueAsType(SsCellValueJavaType.String, null, headerText);
            cell.styleAsHeader();
            this.autoSizeColumn(columnIndex);
            columnIndex++;
        }
        return header;
    }


    /**
     * @param headerMap
     * @param bean
     * @param recordIndex             0-based
     * @param rowIndex                0-based
     * @param datumErrDisplayFunction if there is an error for a single property, conver the error to a text and put it in the cell
     * @param datumErrors             an output parameter
     * @param <BEAN>
     * @return
     */
    default <BEAN> SsRow createDataRow(LinkedHashMap<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, Function<DatumError, String> datumErrDisplayFunction, List<DatumError> datumErrors) {
        SsRow row = this.createNewRow(rowIndex);
        int columnIndex = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {

            SsCell cell = row.createCell(columnIndex);

            String propName = entry.getKey();

            try {
                SsCellValueJavaType javaType = SsCellValueHelper.resolveJavaTypeOfPropertyOrThrow(bean, propName);
                Class<Enum<?>> enumClassIfEnum = getPropertyEnumClassIfEnum(bean, propName);
                Object propValue = PropertyUtils.getProperty(bean, propName);
                cell.writeValueAsType(javaType, enumClassIfEnum, propValue);
            } catch (Exception e) {
                this.getLogger().warn("Datum error", e);

                DatumError de = new DatumError();
                de.setPropName(propName);
                de.setRecordIndex(recordIndex);
                de.setCause(e);
                datumErrors.add(de);

                if (datumErrDisplayFunction != null) {
                    String datumErrorDisplayText = datumErrDisplayFunction.apply(de);
                    try {
                        cell.writeValueAsType(SsCellValueJavaType.String, null, datumErrorDisplayText);
                    } catch (RuntimeException errDisplayException) {
                        this.getLogger().error("Failed to put datum error to a cell", errDisplayException);
                    }
                }
                cell.styleAsError();
            }
            columnIndex++;
        }

        return row;
    }

    void autoSizeColumn(int columnIndex);

    Logger getLogger();

    String getSheetName();
}
