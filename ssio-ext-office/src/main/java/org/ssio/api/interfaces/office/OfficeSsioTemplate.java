package org.ssio.api.interfaces.office;

import org.ssio.api.interfaces.AbstractSsioTemplate;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.office.parse.OfficeParseParamBuilder;
import org.ssio.api.interfaces.office.save.OfficeSaveParamBuilder;
import org.ssio.api.interfaces.parse.CellsErrorDuringParseException;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.save.DataErrorDuringSaveException;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.spi.impl.office.factory.OfficeWorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class OfficeSsioTemplate extends AbstractSsioTemplate {
    private static final OfficeSsioTemplate DEFAULT_INSTANCE =
            new OfficeSsioTemplate(SsioManager.newInstance(OfficeWorkbookFactory.defaultInstance()));


    public static OfficeSsioTemplate defaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private SsioManager ssioManager;

    public OfficeSsioTemplate(SsioManager ssioManager) {
        this.ssioManager = ssioManager;
    }


    /**
     * @param stillSaveIfDataError See {@link AbstractSsioTemplate#save(Supplier, Collection, Class, OutputStream, boolean)}
     */
    public <BEAN> void toSheet(Collection<BEAN> beans,
                               Class<BEAN> beanClass,
                               OutputStream outputTarget,
                               boolean stillSaveIfDataError) throws DataErrorDuringSaveException, IOException {
        Supplier<SaveParamBuilder> paramBuilderSupplier = () -> new OfficeSaveParamBuilder();
        super.save(paramBuilderSupplier,
                beans, beanClass, outputTarget, stillSaveIfDataError);
    }


    /**
     * @param throwIfCellsError See {@link AbstractSsioTemplate#parse(Supplier, InputStream, Class, boolean)}
     */
    public <BEAN> List<BEAN> toBeans(InputStream input,
                                     Class<BEAN> beanClass, boolean throwIfCellsError)
            throws CellsErrorDuringParseException, IOException {
        Supplier<ParseParamBuilder> paramBuilderSupplier = () -> new OfficeParseParamBuilder();
        return super.parse(paramBuilderSupplier, input, beanClass, throwIfCellsError);
    }

    @Override
    protected SsioManager getSsioManager() {
        return ssioManager;
    }
}