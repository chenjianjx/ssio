package org.ssio.api.interfaces.save;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveParamBuilderTest {





    @Test
    void build_allNull() {
        TestSaveParamBuilder<String> builder = new TestSaveParamBuilder<String>().setDatumErrDisplayFunction(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("beans cannot be null"));
        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("outputTarget cannot be null"));
        assertTrue(e.getMessage().contains("datumErrDisplayFunction cannot be null"));
    }

    @Test
    void toStringTest(){
        TestSaveParamBuilder<String> builder = new TestSaveParamBuilder<String>().setDatumErrDisplayFunction(null);
        assertNotNull(builder.toString());
    }

}