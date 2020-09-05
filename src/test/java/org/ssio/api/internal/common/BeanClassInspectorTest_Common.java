package org.ssio.api.internal.common;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.ssio.api.external.SsioApiConstants;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.typing.ComplexTypeHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ssio.testutil.SsioTestUtils.havingErrorContains;

class BeanClassInspectorTest_Common {

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
        private Date justDate;
        @SsColumn(index = 1, typeHandler = DateAsLongHandler.class)
        private Long dateAsLong;
        @SsColumn(index = 2, typeHandler = CompositeFieldTypeHandler.class)
        private CompositeField compositeFieldWithHandler;
        @SsColumn(index = 3)
        private CompositeField compositeFieldWithoutHandler;
        @SsColumn(index = 4, typeHandler = CompositeFieldIllegalHandler.class)
        private CompositeField compositeFieldWithIllegalHandler;


        public Date getJustDate() {
            return justDate;
        }

        public void setJustDate(Date justDate) {
            this.justDate = justDate;
        }

        public Long getDateAsLong() {
            return dateAsLong;
        }

        public void setDateAsLong(Long dateAsLong) {
            this.dateAsLong = dateAsLong;
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

        public CompositeField getCompositeFieldWithIllegalHandler() {
            return compositeFieldWithIllegalHandler;
        }

        public void setCompositeFieldWithIllegalHandler(CompositeField compositeFieldWithIllegalHandler) {
            this.compositeFieldWithIllegalHandler = compositeFieldWithIllegalHandler;
        }

        public static class CompositeField {
            public String foo;
            public String bar;
        }

        public static class CompositeFieldTypeHandler implements ComplexTypeHandler<CompositeField, String> {

            @Override
            public Class<String> getTargetSimpleType() {
                return String.class;
            }

            @Override
            public String nonNullValueToSimple(CompositeField complexTypeValue) {
                return complexTypeValue.foo + "," + complexTypeValue.bar;
            }

            @Override
            public String nullValueToSimple() {
                return null;
            }


            @Override
            public CompositeField fromNullSimpleTypeValue() {
                return null;
            }

            @Override
            public CompositeField fromNonNullSimpleTypeValue(String simpleTypeValue) {
                CompositeField cf = new CompositeField();
                cf.foo = StringUtils.split(simpleTypeValue, ",")[0];
                cf.bar = StringUtils.split(simpleTypeValue, ",")[1];
                return cf;
            }
        }

        public static class DateAsLongHandler implements ComplexTypeHandler<Date, Long> {

            @Override
            public Class<Long> getTargetSimpleType() {
                return Long.class;
            }

            @Override
            public Long nonNullValueToSimple(Date complexTypeValue) {
                return complexTypeValue.getTime();
            }

            @Override
            public Long nullValueToSimple() {
                return null;
            }

            @Override
            public Date fromNonNullSimpleTypeValue(Long simpleTypeValue) {
                return new Date(simpleTypeValue);
            }

            @Override
            public Date fromNullSimpleTypeValue() {
                return null;
            }
        }


        public static class CompositeFieldIllegalHandler implements ComplexTypeHandler<CompositeField, Object> {

            @Override
            public Class<Object> getTargetSimpleType() {
                return Object.class;
            }

            @Override
            public Object nonNullValueToSimple(CompositeField complexTypeValue) {
                return complexTypeValue;
            }

            @Override
            public Object nullValueToSimple() {
                return null;
            }

            @Override
            public CompositeField fromNonNullSimpleTypeValue(Object simpleTypeValue) {
                return (CompositeField) simpleTypeValue;
            }

            @Override
            public CompositeField fromNullSimpleTypeValue() {
                return null;
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
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappings(NoColumnBean.class, SsioMode.SAVE, null, errors);
        System.out.println(errors);
        assertEquals(0, pacList.size());
        assertTrue(errors.get(0).contains("at least one"));
    }

    @Test
    void getMappings_typeHandling() {
        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappings(PropTypeHandlerTestBean.class, SsioMode.SAVE, null, errors);
        errors.forEach(e -> System.err.println(e));

        assertEquals(3, pacList.size());
        assertEquals(null, pacList.get(0).getTypeHandlerClass());
        assertEquals(PropTypeHandlerTestBean.DateAsLongHandler.class, pacList.get(1).getTypeHandlerClass());
        assertEquals(PropTypeHandlerTestBean.CompositeFieldTypeHandler.class, pacList.get(2).getTypeHandlerClass());

        assertTrue(havingErrorContains(errors, "compositeFieldWithoutHandler", "is not supported"));
        assertTrue(havingErrorContains(errors, "compositeFieldWithIllegalHandler", "is not supported"));

    }


    @Test
    void getMappings_formatTest() {
        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappings(FormatTestBean.class, SsioMode.SAVE, null, errors);
        System.out.println(errors);

        assertEquals(3, pacList.size());
        assertEquals(SsioApiConstants.DEFAULT_LOCAL_DATE_PATTERN, pacList.get(0).getFormat());
        assertEquals("yyyy-MM-dd", pacList.get(1).getFormat());
        assertEquals(null, pacList.get(2).getFormat());

        assertTrue(havingErrorContains(errors, "an invalid date format", "total-nonsense", "invalidFormat"));
    }

}