package org.ssio.api.interfaces.htmltable.save;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HtmlElementAttributesTest {
    @Test
    void getTableAttributes() {
        HtmlElementAttributes attributes = new HtmlElementAttributes(null);
        assertNotNull(attributes.getTableAttributes());
    }

    @Test
    void useBuilder() {
        HtmlElementAttributes attributes = HtmlElementAttributes.neoBuilder()
                .setOneTableAttribute("id", "someId")
                .setOneTableAttribute("class", "someClass")
                .build();


        Map<String, String> tableAttributes = attributes.getTableAttributes();
        assertEquals(2, tableAttributes.size());
        assertEquals("someId", tableAttributes.get("id"));
        assertEquals("someClass", tableAttributes.get("class"));
    }
}
