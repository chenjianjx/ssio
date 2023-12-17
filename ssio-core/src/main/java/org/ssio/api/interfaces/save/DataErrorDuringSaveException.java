package org.ssio.api.interfaces.save;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class DataErrorDuringSaveException extends RuntimeException {

    private final List<DatumError> datumErrors;

    public DataErrorDuringSaveException(List<DatumError> datumErrors) {
        this.datumErrors = datumErrors == null ? Collections.emptyList() : datumErrors;
    }

    public List<DatumError> getDatumErrors() {
        return datumErrors;
    }

    @Override
    public String getMessage() {
        return StringUtils.join(datumErrors, "\n");
    }
}
