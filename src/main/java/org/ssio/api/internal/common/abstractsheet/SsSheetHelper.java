package org.ssio.api.internal.common.abstractsheet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.external.save.DatumError;
import org.ssio.api.external.typing.ComplexTypeHandler;
import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.api.internal.common.PropAndColumn;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;
import org.ssio.spi.developerexternal.abstractsheet.model.SsRow;
import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;

import java.util.List;
import java.util.function.Function;

import static org.ssio.util.lang.SsioReflectionUtils.createInstance;

/**
 * some file-type-independent logic for sheets
 */
public class SsSheetHelper {

    private static final Logger logger = LoggerFactory.getLogger(SsSheetHelper.class);

    private SsCellHelper cellHelper;

    public SsSheetHelper(SsCellHelper cellHelper) {
        this.cellHelper = cellHelper;
    }

    public SsRow createHeaderRow(SsSheet sheet, List<PropAndColumn> propAndColumnList) {

        SsRow header = sheet.createNewRow(0);
        for (PropAndColumn pac : propAndColumnList) {
            String headerText = StringUtils.defaultString(pac.getColumnName());
            SsCell cell = header.createCell(pac.getColumnIndex());
            cellHelper.writeValueAsType(cell, SimpleTypeEnum.String, null, null, headerText);
            cell.styleAsHeader();
            sheet.autoSizeColumn(pac.getColumnIndex());
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
    public <BEAN> SsRow createDataRow(SsSheet sheet, List<PropAndColumn> propAndColumnList, BEAN bean, int recordIndex, int rowIndex, Function<DatumError, String> datumErrDisplayFunction, List<DatumError> datumErrors) {
        SsRow row = sheet.createNewRow(rowIndex);

        for (PropAndColumn propAndColumn : propAndColumnList) {

            SsCell cell = row.createCell(propAndColumn.getColumnIndex());

            String propName = propAndColumn.getPropName();


            try {
                Object propValue = PropertyUtils.getProperty(bean, propName);
                if (propAndColumn.getTypeHandlerClass() != null) {
                    ComplexTypeHandler handler = createInstance(propAndColumn.getTypeHandlerClass());
                    if (propValue == null) {
                        propValue = handler.nullValueToSimple();
                    } else {
                        propValue = handler.nonNullValueToSimple(propValue);
                    }
                }
                cellHelper.writeValueAsType(cell, propAndColumn.getSimpleTypeEnum(), propAndColumn.getEnumClassIfEnum(), propAndColumn.getFormat(), propValue);
            } catch (Exception e) {
                logger.warn("Datum error", e);

                DatumError de = new DatumError();
                de.setPropName(propName);
                de.setRecordIndex(recordIndex);
                de.setCause(e);
                datumErrors.add(de);

                if (datumErrDisplayFunction != null) {
                    String datumErrorDisplayText = datumErrDisplayFunction.apply(de);
                    try {
                        cellHelper.writeValueAsType(cell, SimpleTypeEnum.String, null, null, datumErrorDisplayText);
                    } catch (RuntimeException errDisplayException) {
                        logger.error("Failed to put datum error to a cell", errDisplayException);
                    }
                }
                cell.styleAsError();
            }

        }

        return row;
    }
}
