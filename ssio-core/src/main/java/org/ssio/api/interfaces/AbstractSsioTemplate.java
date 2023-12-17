package org.ssio.api.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.interfaces.parse.CellsErrorDuringParseException;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.api.interfaces.save.DataErrorDuringSaveException;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractSsioTemplate {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSsioTemplate.class);

    /**
     * @param stillSaveIfDataError what to do if there are data errors ?
     *                             * if this flag is true, then beans will be saved anyway, and no {@link DataErrorDuringSaveException} will be thrown
     *                             * if this flag is false, then beans will not be saved, and {@link DataErrorDuringSaveException} will be thrown
     * @throws DataErrorDuringSaveException
     */
    protected <BEAN> void save(Supplier<SaveParamBuilder> paramBuilderSupplier,
                               Collection<BEAN> beans,
                               Class<BEAN> beanClass,
                               OutputStream outputTarget,
                               boolean stillSaveIfDataError
    ) throws DataErrorDuringSaveException, IOException {

        SaveParamBuilder paramBuilder = paramBuilderSupplier.get().setBeans(beans)
                .setBeanClass(beanClass)
                .setOutputTarget(outputTarget)
                .setCreateHeader(true)
                .setStillSaveIfDataError(stillSaveIfDataError);

        SaveParam saveParam = paramBuilder.build();
        SaveResult saveResult = getSsioManager().save(saveParam);
        handleSaveResult(stillSaveIfDataError, saveResult);
    }


    /**
     * @param throwIfCellsError what to do if there are cell errors ?
     *                          * if this flag is true, a {@link CellsErrorDuringParseException} will be thrown
     *                          * if this flag is false, no {@link CellsErrorDuringParseException} will be thrown
     * @throws CellsErrorDuringParseException
     */
    public <BEAN> List<BEAN> parse(Supplier<ParseParamBuilder> paramBuilderSupplier, InputStream input,
                                   Class<BEAN> beanClass, boolean throwIfCellsError) throws CellsErrorDuringParseException, IOException {
        ParseParam<BEAN> parseParam =
                paramBuilderSupplier.get()
                        .setBeanClass(beanClass)
                        .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME)
                        .setSpreadsheetInput(input)
                        .setSheetHasHeader(true)
                        .build();
        ParseResult<BEAN> parseResult = getSsioManager().parse(parseParam);
        return extractBeansOrThrow(throwIfCellsError, parseResult);
    }


    protected abstract SsioManager getSsioManager();

    private static void handleSaveResult(boolean stillSaveIfDataError, SaveResult saveResult) {
        if (saveResult.hasDatumErrors()) {
            if (stillSaveIfDataError) {
                logger.warn("Data errors during saving: " + StringUtils.join(saveResult.getDatumErrors(), "\n"));
            } else {
                throw new DataErrorDuringSaveException(saveResult.getDatumErrors());
            }
        }
    }


    private static <BEAN> List<BEAN> extractBeansOrThrow(boolean throwIfCellsError, ParseResult<BEAN> parseResult) {
        if (parseResult.hasCellErrors()) {
            if (throwIfCellsError) {
                throw new CellsErrorDuringParseException(parseResult.getCellErrors());
            } else {
                logger.warn("Cell errors during parsing: " + StringUtils.join(parseResult.getCellErrors(), "\n"));
                return parseResult.getBeans();
            }
        }
        return parseResult.getBeans();
    }
}