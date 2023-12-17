package org.ssio.api.interfaces.parse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParseParamTest {

    @Test
    void testToString() {

        TestParseParam param = new TestParseParam(null, null, null, false);
        assertNotNull(param.toString());

    }
}