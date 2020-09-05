package org.ssio.spi.developerexternal.abstractsheet.model;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.api.internal.common.abstractsheet.SsCellHelper;
import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;

/**
 * Note there are no reading/writing methods here.  They are defined in {@link SsCellHelper}
 */
public interface SsCell {

    SsCellValueBinder getCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum);

    void styleAsError();

    void styleAsHeader();
}
