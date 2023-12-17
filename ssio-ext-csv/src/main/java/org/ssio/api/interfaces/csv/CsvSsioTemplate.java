package org.ssio.api.interfaces.csv;

import org.ssio.api.interfaces.AbstractSsioTemplate;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.csv.parse.CsvParseParamBuilder;
import org.ssio.api.interfaces.csv.save.CsvSaveParamBuilder;
import org.ssio.api.interfaces.parse.CellsErrorDuringParseException;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.save.DataErrorDuringSaveException;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.spi.impl.csv.factory.CsvWorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CsvSsioTemplate extends AbstractSsioTemplate {
    private static final CsvSsioTemplate DEFAULT_INSTANCE =
            new CsvSsioTemplate(SsioManager.newInstance(CsvWorkbookFactory.defaultInstance()));


    public static CsvSsioTemplate defaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private SsioManager ssioManager;

    public CsvSsioTemplate(SsioManager ssioManager) {
        this.ssioManager = ssioManager;
    }


    /**
     * @param stillSaveIfDataError See {@link AbstractSsioTemplate#save(Supplier, Collection, Class, OutputStream, boolean)}
     */
    public <BEAN> void toCsv(Collection<BEAN> beans,
                             Class<BEAN> beanClass,
                             OutputStream outputTarget,
                             String outputCharset,
                             boolean stillSaveIfDataError) throws DataErrorDuringSaveException, IOException {
        Supplier<SaveParamBuilder> paramBuilderSupplier = () -> new CsvSaveParamBuilder().setOutputCharset(outputCharset);
        super.save(paramBuilderSupplier,
                beans, beanClass, outputTarget, stillSaveIfDataError);
    }


    /**
     * @param stillSaveIfDataError See {@link AbstractSsioTemplate#save(Supplier, Collection, Class, OutputStream, boolean)}
     */
    public <BEAN> String toCsvString(Collection<BEAN> beans,
                                     Class<BEAN> beanClass,
                                     boolean stillSaveIfDataError) throws IOException {
        try (ByteArrayOutputStream outputTarget = new ByteArrayOutputStream()) {
            String outputCharset = Charset.defaultCharset().toString();
            this.toCsv(beans, beanClass, outputTarget, outputCharset, stillSaveIfDataError);
            return outputTarget.toString(outputCharset);
        }
    }


    /**
     * @param throwIfCellsError See {@link AbstractSsioTemplate#parse(Supplier, InputStream, Class, boolean)}
     */
    public <BEAN> List<BEAN> toBeans(InputStream input, String inputCharset,
                                     Class<BEAN> beanClass, boolean throwIfCellsError)
            throws CellsErrorDuringParseException, IOException {
        Supplier<ParseParamBuilder> paramBuilderSupplier = () -> new CsvParseParamBuilder().setInputCharset(inputCharset);
        return super.parse(paramBuilderSupplier, input, beanClass, throwIfCellsError);
    }

    /**
     * @param throwIfCellsError See {@link AbstractSsioTemplate#parse(Supplier, InputStream, Class, boolean)}
     */
    public <BEAN> List<BEAN> toBeans(String csvString,
                                     Class<BEAN> beanClass,
                                     boolean throwIfCellsError) throws CellsErrorDuringParseException, IOException {
        String inputCharset = Charset.defaultCharset().toString();
        try (ByteArrayInputStream input = new ByteArrayInputStream(csvString.getBytes(inputCharset))) {
            return toBeans(input, inputCharset, beanClass, throwIfCellsError);
        }
    }

    @Override
    protected SsioManager getSsioManager() {
        return ssioManager;
    }
}