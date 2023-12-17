package org.ssio.spi.interfaces.model.write;

import org.ssio.api.impl.common.abstractsheet.CellHelper;
import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;

/**
 * Note there are no reading/writing methods here.  They are defined in {@link CellHelper}
 */
public interface WritableCell {

    CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum);

    void styleAsError();

    void styleAsHeader();
}
