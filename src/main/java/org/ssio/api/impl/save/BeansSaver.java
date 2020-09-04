package org.ssio.api.impl.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.impl.common.BeanClassInspector;
import org.ssio.api.impl.common.PropAndColumn;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.spi.interfaces.abstractsheet.factory.SsWorkbookFactoryRegistry;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToSaveFactory;
import org.ssio.spi.interfaces.abstractsheet.model.SsSheet;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeansSaver {

    private static final Logger logger = LoggerFactory.getLogger(BeansSaver.class);
    private final SsWorkbookFactoryRegistry workbookFactoryRegistry;

    private BeanClassInspector beanClassInspector = new BeanClassInspector();

    public BeansSaver(SsWorkbookFactoryRegistry workbookFactoryRegistry) {
        this.workbookFactoryRegistry = workbookFactoryRegistry;
    }

    public <BEAN> SaveResult doSave(SaveParam<BEAN> param) throws IOException {

        List<String> beanClassErrors = new ArrayList<>();
        List<PropAndColumn> propAndColumnList = beanClassInspector.getPropAndColumnMappingsForSaveMode(param.getBeanClass(), beanClassErrors);
        if (beanClassErrors.size() > 0) {
            //shouldn't happen here. The validation has been done before this is executed
            throw new IllegalArgumentException(beanClassErrors.toString());
        }

        WorkbookToSaveFactory workbookFactory = workbookFactoryRegistry.getWorkbookToSaveFactory(param.getClass());
        if (workbookFactory == null) {
            throw new IllegalStateException("There is no workbook factory registered for param class: " + param.getClass());
        }


        SsWorkbook workbook = workbookFactory.newWorkbook(param);
        SsSheet sheet =  workbook.createNewSheet();

        int numOfBeans = param.getBeans().size();
        logger.info(numOfBeans + " beans will be written to a spreadsheet");

        SaveResult result = new SaveResult();

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
            workbook.write(param.getOutputTarget()); //
            logger.info("Beans written to target spreadsheet. " + result.getStats());
        } else {
            logger.warn("Beans won't be written." + result.getStats());
        }
        return result;
    }


    /**
     * the workbook has been generated. Should we write it to the outputstream?
     */
    private boolean shouldWriteToTarget(SaveParam<?> param, SaveResult result) {
        if (param.isStillSaveIfDataError()) {
            return true;
        }
        return result.hasNoDatumErrors();
    }
}
