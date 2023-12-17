package org.ssio.spi.interfaces.model.read;

import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;


public interface ReadableCell {
    CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum);
    boolean isBlank();
}
