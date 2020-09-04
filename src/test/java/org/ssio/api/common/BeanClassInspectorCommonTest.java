package org.ssio.api.common;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.ssio.api.common.annotation.SsColumn;
import org.ssio.api.common.mapping.PropAndColumn;
import org.ssio.api.common.typing.SsioComplexTypeHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ssio.testutil.SsioTestUtils.havingErrorContains;

class BeanClassInspectorCommonTest {

    BeanClassInspector inspector = new BeanClassInspector();

    public static class NoColumnBean {
        private String foo;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }
    }

    public static class PropTypeHandlerTestBean {
        @SsColumn(index = 0)
        private LocalDate justLocalDate;
        @SsColumn(index = 1)
        private long localDateAsNumber;
        @SsColumn(index = 2)
        private CompositeField compositeFieldWithHandler;
        @SsColumn(index = 3)
        private CompositeField compositeFieldWithoutHandler;

        public LocalDate getJustLocalDate() {
            return justLocalDate;
        }

        public void setJustLocalDate(LocalDate justLocalDate) {
            this.justLocalDate = justLocalDate;
        }

        public long getLocalDateAsNumber() {
            return localDateAsNumber;
        }

        public void setLocalDateAsNumber(long localDateAsNumber) {
            this.localDateAsNumber = localDateAsNumber;
        }

        public CompositeField getCompositeFieldWithHandler() {
            return compositeFieldWithHandler;
        }

        public void setCompositeFieldWithHandler(CompositeField compositeFieldWithHandler) {
            this.compositeFieldWithHandler = compositeFieldWithHandler;
        }

        public CompositeField getCompositeFieldWithoutHandler() {
            return compositeFieldWithoutHandler;
        }

        public void setCompositeFieldWithoutHandler(CompositeField compositeFieldWithoutHandler) {
            this.compositeFieldWithoutHandler = compositeFieldWithoutHandler;
        }

        public static class CompositeField {
            public String foo;
            public String bar;
        }

        public static class CompositeFieldTypeHandler implements SsioComplexTypeHandler<CompositeField> {

            @Override
            public Class<String> getTargetSimpleType() {
                return String.class;
            }

            @Override
            public String toSimpleTypeValue(CompositeField originalValue) {
                if (originalValue == null) {
                    return null;
                }

                return originalValue.foo + "," + originalValue.bar;
            }

            @Override
            public CompositeField fromSimpleTypeValue(Object simpleTypeValue) {
                String stv = (String) simpleTypeValue;
                CompositeField cf = new CompositeField();
                cf.foo = StringUtils.split(stv, ",")[0];
                cf.bar = StringUtils.split(stv, ",")[1];
                return cf;
            }
        }

        public static class DateAsLongHandler implements SsioComplexTypeHandler<Date> {

            @Override
            public Class getTargetSimpleType() {
                return long.class;
            }

            @Override
            public Object toSimpleTypeValue(Date originalValue) {
                if (originalValue == null) {
                    return 0;
                } else {
                    return originalValue.getTime();
                }
            }

            @Override
            public Date fromSimpleTypeValue(Object simpleTypeValue) {
                return new Date((long) simpleTypeValue);
            }
        }
    }

    public static class FormatTestBean {
        @SsColumn(index = 0)
        private LocalDate formatNotSet;

        @SsColumn(index = 1, format = "yyyy-MM-dd")
        private LocalDate validFormat;

        @SsColumn(index = 2, format = "total-nonsense")
        private LocalDate invalidFormat;

        @SsColumn(index = 3, format = "yyyy-MM-dd")
        private String notDateTyped;

        public LocalDate getFormatNotSet() {
            return formatNotSet;
        }

        public void setFormatNotSet(LocalDate formatNotSet) {
            this.formatNotSet = formatNotSet;
        }

        public LocalDate getValidFormat() {
            return validFormat;
        }

        public void setValidFormat(LocalDate validFormat) {
            this.validFormat = validFormat;
        }

        public LocalDate getInvalidFormat() {
            return invalidFormat;
        }

        public void setInvalidFormat(LocalDate invalidFormat) {
            this.invalidFormat = invalidFormat;
        }

        public String getNotDateTyped() {
            return notDateTyped;
        }

        public void setNotDateTyped(String notDateTyped) {
            this.notDateTyped = notDateTyped;
        }
    }

    @Test
    void getMappings_noColumn() {
        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForBeans2Sheet(NoColumnBean.class, errors);
        System.out.println(errors);
        assertEquals(0, pacList.size());
        assertTrue(errors.get(0).contains("at least one"));
    }

    @Test
    void getMappings_unsupportedPropType() {
        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForBeans2Sheet(PropTypeHandlerTestBean.class, errors);
        System.out.println(errors);
        assertEquals(0, pacList.size());
        assertTrue(havingErrorContains(errors, "not supported", "java.lang.Object", "foo"));
    }


    @Test
    void getMappings_formatTest() {
        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForBeans2Sheet(FormatTestBean.class, errors);
        System.out.println(errors);

        assertEquals(3, pacList.size());
        assertEquals(SsioConstants.DEFAULT_LOCAL_DATE_PATTERN, pacList.get(0).getFormat());
        assertEquals("yyyy-MM-dd", pacList.get(1).getFormat());
        assertEquals(null, pacList.get(2).getFormat());

        assertTrue(havingErrorContains(errors, "an invalid date format", "total-nonsense", "invalidFormat"));
    }

}