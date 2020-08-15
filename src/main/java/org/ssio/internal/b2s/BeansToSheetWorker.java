package org.ssio.internal.b2s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.abstractsheet.DefaultSsFactory;
import org.ssio.api.abstractsheet.SsFactory;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.abstractsheet.SsWorkbook;
import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.internal.temp.HeaderUtils;

import java.io.IOException;
import java.util.Map;

public class BeansToSheetWorker {

    private static final Logger logger = LoggerFactory.getLogger(BeansToSheetWorker.class);

    public <BEAN> BeansToSheetResult doWork(BeansToSheetParam<BEAN> param) throws IOException {

        Map<String, String> headerMap = HeaderUtils.generateHeaderMapFromProps(param.getBeanClass());

        SsFactory ssFactory = new DefaultSsFactory();

        SsWorkbook workbook = ssFactory.newWorkbook(param.getFileType());
        SsSheet sheet = workbook.createSheet(param.getSheetName());

        int numOfBeans = param.getBeans().size();
        logger.info(numOfBeans + " beans will be written to a spreadsheet");

        BeansToSheetResult result = new BeansToSheetResult();

        sheet.createHeaderRow(headerMap);
        logger.debug("Header row created");


        int recordIndex = 0;
        for (BEAN bean : param.getBeans()) {
            int rowIndex = recordIndex + 1;
            sheet.createDataRow(headerMap, bean, recordIndex, rowIndex, param.getDatumErrPlaceholder(), result.getDatumErrors());
            logger.debug("Row created for record " + (recordIndex + 1) + "/" + numOfBeans);
            recordIndex++;
        }

        if (shouldWriteToTarget(param, result)) {
            workbook.write(param.getOutputTarget());
            logger.info("Beans written to target spreadsheet. " + result.getStats());
        } else {
            logger.warn("Beans won't be written." + result.getStats());
        }
        return result;
    }


    /**
     * the workbook has been generated. Should we write it to the outputstream?
     */
    private boolean shouldWriteToTarget(BeansToSheetParam<?> param, BeansToSheetResult result) {
        if (param.isStillSaveIfDataError()) {
            return true;
        }
        return result.hasNoDatumErrors();
    }
}
