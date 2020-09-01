package org.ssio.internal.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SsioReflectionHelperTest {

    public static class ExtractPropertyNameTestBean {

        public boolean isBoolean() {
            return false;
        }

        public boolean isBooleanWithParam(String param) {
            return false;
        }

        public int isBooleanNotReturningBoolean() {
            return 0;
        }


        public Object getSomething() {
            return false;
        }

        public Object getSomethingWithParam(String param) {
            return false;
        }

        public boolean getSomethingReturningBoolean() {
            return false;
        }

        public void getSomethingReturningVoid() {

        }


        public void setSomething(String foo) {

        }

        public void setSomethingWithoutParam() {

        }

        static Method getMethodByName(String name) {
            return Arrays.stream(ExtractPropertyNameTestBean.class.getMethods()).filter(m -> m.getName().equals(name)).findFirst().get();
        }
    }

    @Test
    void extractPropertyName() {
        assertEquals("boolean", SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("isBoolean")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("isBooleanWithParam")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("isBooleanNotReturningBoolean")));
        assertEquals("something", SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("getSomething")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("getSomethingWithParam")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("getSomethingReturningBoolean")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("getSomethingReturningVoid")));
        assertEquals("something", SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("setSomething")));
        assertEquals(null, SsioReflectionHelper.extractPropertyName(ExtractPropertyNameTestBean.getMethodByName("setSomethingWithoutParam")));


    }
}