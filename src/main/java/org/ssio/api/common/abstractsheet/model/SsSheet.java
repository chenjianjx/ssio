package org.ssio.api.common.abstractsheet.model;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.ssio.api.b2s.DatumError;
import org.ssio.api.common.mapping.PropAndColumn;
import org.ssio.api.common.typing.SsioSimpleTypeEnum;

import java.util.List;
import java.util.function.Function;

import static org.ssio.internal.util.SsioReflectionHelper.createInstance;

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


    default SsRow createHeaderRow(List<PropAndColumn> propAndColumnList) {

        SsRow header = this.createNewRow(0);
        for (PropAndColumn pac : propAndColumnList) {
            String headerText = StringUtils.defaultString(pac.getColumnName());
            SsCell cell = header.createCell(pac.getColumnIndex());
            cell.writeValueAsType(SsioSimpleTypeEnum.String, null, null, headerText);
            cell.styleAsHeader();
            this.autoSizeColumn(pac.getColumnIndex());
        }

        return header;
    }


    /**
     * @param propAndColumnList
     * @param bean
     * @param recordIndex             0-based
     * @param rowIndex                0-based
     * @param datumErrDisplayFunction if there is an error for a single property, conver the error to a text and put it in the cell
     * @param datumErrors             an output parameter
     * @param <BEAN>
     * @return
     */
    default <BEAN> SsRow createDataRow(List<PropAndColumn> propAndColumnList, BEAN bean, int recordIndex, int rowIndex, Function<DatumError, String> datumErrDisplayFunction, List<DatumError> datumErrors) {
        SsRow row = this.createNewRow(rowIndex);

        for (PropAndColumn propAndColumn : propAndColumnList) {

            SsCell cell = row.createCell(propAndColumn.getColumnIndex());

            String propName = propAndColumn.getPropName();


            try {
                Object propValue = PropertyUtils.getProperty(bean, propName);
                if (propAndColumn.getTypeHandlerClass() != null) {
                    propValue = createInstance(propAndColumn.getTypeHandlerClass()).toSimpleTypeValue(propValue);
                }
                cell.writeValueAsType(propAndColumn.getSimpleTypeEnum(), propAndColumn.getEnumClassIfEnum(), propAndColumn.getFormat(), propValue);
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
                        cell.writeValueAsType(SsioSimpleTypeEnum.String, null, null, datumErrorDisplayText);
                    } catch (RuntimeException errDisplayException) {
                        this.getLogger().error("Failed to put datum error to a cell", errDisplayException);
                    }
                }
                cell.styleAsError();
            }

        }

        return row;
    }

    void autoSizeColumn(int columnIndex);

    Logger getLogger();

    String getSheetName();
}
