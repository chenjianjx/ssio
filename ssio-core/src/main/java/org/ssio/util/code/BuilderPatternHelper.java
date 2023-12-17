package org.ssio.util.code;

import java.util.List;

public class BuilderPatternHelper {

    public void validateFieldNotNull(String fieldName, Object value, List<String> errors) {
        if (value == null) {
            errors.add("The value of " + fieldName + " cannot be null");
        }
    }
}
