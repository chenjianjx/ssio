package org.ssio.spi.interfaces.abstractsheet.model;

import org.ssio.api.interfaces.typing.SimpleTypeEnum;
import org.ssio.spi.interfaces.abstractsheet.cellvaluebinder.SsCellValueBinder;

public interface SsCell {



    SsCellValueBinder getCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum);

    void styleAsError();

    void styleAsHeader();
}
