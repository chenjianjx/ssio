package org.ssio.api.interfaces.parse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseResultTest {

    @Test
    void testToString() {
        assertNotNull(new ParseResult<>().toString());
    }
}