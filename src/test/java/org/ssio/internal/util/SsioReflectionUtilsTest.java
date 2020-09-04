package org.ssio.internal.util;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.ssio.util.lang.SsioReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SsioReflectionUtilsTest {

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


        public String getSomething() {
            return "haha";
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
        assertEquals(Pair.of("boolean", boolean.class), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("isBoolean")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("isBooleanWithParam")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("isBooleanNotReturningBoolean")));
        assertEquals(Pair.of("something", String.class), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("getSomething")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("getSomethingWithParam")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("getSomethingReturningBoolean")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("getSomethingReturningVoid")));
        assertEquals(Pair.of("something", String.class), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("setSomething")));
        assertEquals(Pair.of(null, null), SsioReflectionUtils.extractPropertyNameAndType(ExtractPropertyNameTestBean.getMethodByName("setSomethingWithoutParam")));


    }
}