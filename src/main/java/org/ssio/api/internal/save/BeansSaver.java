package org.ssio.api.internal.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.external.save.SaveParam;
import org.ssio.api.external.save.SaveResult;
import org.ssio.api.internal.common.BeanClassInspector;
import org.ssio.api.internal.common.PropAndColumn;
import org.ssio.api.internal.common.abstractsheet.SsCellHelper;
import org.ssio.api.internal.common.abstractsheet.SsSheetHelper;
import org.ssio.spi.clientexternal.spiregistry.SsWorkbookFactoryRegistry;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;
import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeansSaver {

    private static final Logger logger = LoggerFactory.getLogger(BeansSaver.class);
    private final SsWorkbookFactoryRegistry workbookFactoryRegistry;

    private BeanClassInspector beanClassInspector = new BeanClassInspector();
    private SsSheetHelper sheetHelper = new SsSheetHelper(new SsCellHelper());

    public BeansSaver(SsWorkbookFactoryRegistry workbookFactoryRegistry) {
        this.workbookFactoryRegistry = workbookFactoryRegistry;
    }

    public <BEAN> SaveResult doSave(SaveParam<BEAN> param) throws IOException {

        List<String> beanClassErrors = new ArrayList<>();
        List<PropAndColumn> propAndColumnList = beanClassInspector.getPropAndColumnMappingsForSaveMode(param.getBeanClass(), beanClassErrors);
        if (beanClassErrors.size() > 0) {
            //shouldn't happen here. The validation has been done before here
            throw new IllegalArgumentException(beanClassErrors.toString());
        }

        SsWorkbookFactory workbookFactory = workbookFactoryRegistry.getFactory(param.getSpreadsheetFileType());
        if (workbookFactory == null) {
            throw new IllegalStateException("There is no workbook factory registered for file type: " + param.getSpreadsheetFileType());
        }


        SsWorkbook workbook = workbookFactory.newWorkbookForSave(param);
        SsSheet sheet = workbook.createNewSheet();

        int numOfBeans = param.getBeans().size();
        logger.info(numOfBeans + " beans will be written to a spreadsheet");

        SaveResult result = new SaveResult();

        int rowIndex = 0;
        if (param.isCreateHeader()) {
            sheetHelper.createHeaderRow(sheet, propAndColumnList);
            logger.debug("Header row created");
            rowIndex++;
        }


        List<BEAN> beans = new ArrayList<>(param.getBeans());
        for (int recordIndex = 0; recordIndex < beans.size(); recordIndex++) {
            BEAN bean = beans.get(recordIndex);
            sheetHelper.createDataRow(sheet, propAndColumnList, bean, recordIndex, rowIndex, param.getDatumErrDisplayFunction(), result.getDatumErrors());
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
