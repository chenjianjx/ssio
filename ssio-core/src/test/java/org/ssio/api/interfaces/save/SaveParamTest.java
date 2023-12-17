package org.ssio.api.interfaces.save;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SaveParamTest {



    @Test
    void testToString() {

        SaveParam<String> saveParam = new TestSaveParam<>(null, null, null, false, false, null);
        assertNotNull(saveParam.toString());
    }
}