package org.ssio.spi.impl.text.model.read;

import org.apache.commons.lang3.StringUtils;
import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinderRepo;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;
import org.ssio.spi.interfaces.model.read.ReadableCell;

/**
 * A text cell has a text content, not binary
 */
public interface TextReadableCell extends ReadableCell {
    String getContent();

    @Override
    default CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        return TextCellValueBinderRepo.getBinder(javaType, enumClassIfEnum);
    }

    @Override
    default boolean isBlank() {
        return StringUtils.isBlank(getContent());
    }
}
