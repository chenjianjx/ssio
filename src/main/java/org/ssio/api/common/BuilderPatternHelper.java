package org.ssio.api.common;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BuilderPatternHelper {

    public void validateFieldNotNull(String fieldName, Object value, List<String> errors) {
        if (value == null) {
            errors.add("The value of " + fieldName + " cannot be null");
        }
    }

}
