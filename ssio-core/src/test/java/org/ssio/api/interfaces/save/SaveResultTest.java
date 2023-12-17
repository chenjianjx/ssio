package org.ssio.api.interfaces.save;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SaveResultTest {

    @Test
    void testToString() {
        assertNotNull(new SaveResult().toString());
    }
}