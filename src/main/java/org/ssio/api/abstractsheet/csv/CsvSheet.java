package org.ssio.api.abstractsheet.csv;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.abstractsheet.SsCellValueHelper;
import org.ssio.api.abstractsheet.SsCellValueJavaType;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.b2s.DatumError;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinderRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvSheet implements SsSheet {

    private static final Logger logger = LoggerFactory.getLogger(CsvSheet.class);

    private final CSVPrinter csvPrinter;

    public CsvSheet(CSVPrinter csvPrinter) {
        this.csvPrinter = csvPrinter;
    }

    @Override
    public void createHeaderRow(Map<String, String> headerMap) {
        List<String> cells = new ArrayList<>();
        for (String value : headerMap.values()) {
            cells.add(value);
        }

        try {
            csvPrinter.printRecord(cells.toArray());
        } catch (IOException e) {
            //shouldn't happen
            throw new IllegalStateException(e);
        }

    }

    @Override
    public <BEAN> void createDataRow(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, String datumErrPlaceholder, List<DatumError> datumErrors) {
        List<String> cells = new ArrayList<>();

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {

            String propName = entry.getKey();
            try {
                SsCellValueJavaType javaType = SsCellValueHelper.resolveJavaTypeOfPropertyOrThrow(bean, propName);
                CsvCellValueBinder cellValueBinder = CsvCellValueBinderRepo.getCsvCellValueBinder(javaType);
                if (cellValueBinder == null) {
                    throw new IllegalStateException();
                }

                Object propValue = PropertyUtils.getProperty(bean, propName);
                if (propValue == null) {
                    cells.add(null);
                } else {
                    cells.add(cellValueBinder.nonNullValueToCellText(propValue));
                }


            } catch (Exception e) {
                logger.warn("Datum error", e);

                DatumError de = new DatumError();
                de.setPropName(propName);
                de.setRecordIndex(recordIndex);
                de.setCause(e);
                datumErrors.add(de);

                cells.add(datumErrPlaceholder);
            }

        }

        try {
            csvPrinter.printRecord(cells.toArray());
        } catch (IOException e) {
            //shouldn't happen
            throw new IllegalStateException(e);
        }


    }


}
