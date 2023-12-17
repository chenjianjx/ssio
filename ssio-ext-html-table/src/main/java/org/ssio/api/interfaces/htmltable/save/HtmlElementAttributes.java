package org.ssio.api.interfaces.htmltable.save;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;

@Builder(builderMethodName = "neoBuilder",//use a different builderMethodName to differentiate it from param builders' coding style
        setterPrefix = "set")
@Getter
public class HtmlElementAttributes {

    @Singular("oneTableAttribute")
    private Map<String, String> tableAttributes;

    public Map<String, String> getTableAttributes() {
        if (tableAttributes == null) {
            synchronized (this) {
                tableAttributes = new HashMap<>();
            }
        }
        return tableAttributes;
    }
}
