package org.ssio.api.impl.b2s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.b2s.BeansToSheetResult;
import org.ssio.api.impl.common.BeanClassInspector;
import org.ssio.spi.interfaces.abstractsheet.model.DefaultSsFactory;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbookFactory;
import org.ssio.spi.interfaces.abstractsheet.model.SsSheet;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;
import org.ssio.api.impl.common.PropAndColumn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeansToSheetWorker {

    private static final Logger logger = LoggerFactory.getLogger(BeansToSheetWorker.class);

    private BeanClassInspector beanClassInspector = new BeanClassInspector();

    public <BEAN> BeansToSheetResult doWork(BeansToSheetParam<BEAN> param) throws IOException {

        List<String> beanClassErrors = new ArrayList<>();
        List<PropAndColumn> propAndColumnList = beanClassInspector.getPropAndColumnMappingsForBeans2Sheet(param.getBeanClass(), beanClassErrors);
        if (beanClassErrors.size() > 0) {
            //shouldn't happen here. The validation has been done before this is executed
            throw new IllegalArgumentException(beanClassErrors.toString());
        }

        SsWorkbookFactory ssFactory = new DefaultSsFactory();

        SsWorkbook workbook = ssFactory.newWorkbook(param.getFileType(), param.getCellSeparator());
        SsSheet sheet = workbook.createNewSheet(param.getSheetName());

        int numOfBeans = param.getBeans().size();
        logger.info(numOfBeans + " beans will be written to a spreadsheet");

        BeansToSheetResult result = new BeansToSheetResult();

        int rowIndex = 0;
        if (param.isCreateHeader()) {
            sheet.createHeaderRow(propAndColumnList);
            logger.debug("Header row created");
            rowIndex++;
        }


        List<BEAN> beans = new ArrayList<>(param.getBeans());
        for (int recordIndex = 0; recordIndex < beans.size(); recordIndex++) {
            BEAN bean = beans.get(recordIndex);
            sheet.createDataRow(propAndColumnList, bean, recordIndex, rowIndex, param.getDatumErrDisplayFunction(), result.getDatumErrors());
            logger.debug("Row created for record " + (recordIndex + 1) + "/" + numOfBeans);
            rowIndex++;
        }


        if (shouldWriteToTarget(param, result)) {
            workbook.write(param.getOutputTarget(), param.getOutputCharset());
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
