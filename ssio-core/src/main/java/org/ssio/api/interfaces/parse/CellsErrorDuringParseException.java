package org.ssio.api.interfaces.parse;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class CellsErrorDuringParseException extends RuntimeException {

    private final List<CellError> cellErrors;

    public CellsErrorDuringParseException(List<CellError> cellErrors) {
        this.cellErrors = cellErrors == null ? Collections.emptyList() : cellErrors;
    }

    public List<CellError> getCellErrors() {
        return cellErrors;
    }

    @Override
    public String getMessage() {
        return StringUtils.join(cellErrors, "\n");
    }
}
