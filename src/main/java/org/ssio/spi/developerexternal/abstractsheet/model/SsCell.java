package org.ssio.spi.developerexternal.abstractsheet.model;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;

public interface SsCell {



    SsCellValueBinder getCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum);

    void styleAsError();

    void styleAsHeader();
}
