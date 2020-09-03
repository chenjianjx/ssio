package org.ssio.api.common;

import org.junit.jupiter.api.Test;
import org.ssio.api.common.annotation.SsColumn;
import org.ssio.api.common.mapping.PropAndColumn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ssio.testutil.SsioTestUtils.havingErrorContains;

class BeanClassInspectorBeans2SheetTest {

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

    public static class UnsupportedPropTypeBean {
        @SsColumn
        private Object foo;

        public Object getFoo() {
            return foo;
        }

        public void setFoo(Object foo) {
            this.foo = foo;
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


    public static class ParentBean {

        @SsColumn(index = 0, name = "Parent Field")
        private String parentField;

        private String propNotAnnotated;

        public String getParentField() {
            return parentField;
        }

        public String getPropNotAnnotated() {
            return propNotAnnotated;
        }
    }

    public static class ChildBean extends ParentBean {

        @SsColumn(index = 1, name = "Boolean Property")
        private boolean booleanProp;

        @SsColumn(index = 2, name = "String Property")
        private String stringProp;

        @SsColumn(index = 3, name = "Property Without Getter")
        private String propWithoutGetter;

        @SsColumn(name = "Property Without Index")
        private String propWithoutIndex;

        @SsColumn(index = -3, name = "Property With Negative Index")
        private String propWithNegativeIndex;

        @SsColumn(index = 4)
        private String propWithoutColumnName;

        @SsColumn(index = 5, name = "Property by Getter not by Field")
        public String getPropByGetter() {
            return null;
        }

        @SsColumn(index = 6, name = "Fake Property by Getter not by Field")
        public String fakeGetter() {
            return null;
        }

        @SsColumn(index = 7, name = "Property by Setter not by Field")
        public void setPropBySetter(String s) {
        }

        public String getPropBySetter() {
            return null;
        }

        @SsColumn(index = 8, name = "Fake Property by Setter not by Field")
        public void fakeSetter(String s) {

        }

        @SsColumn(index = 50, name = "Property with index 50")
        private String propWithIndex50;

        @SsColumn(index = 50, name = "Property with index 50 As Well")
        private String propWithIndex50AsWell;

        /*generated getters */
        public String getPropWithoutIndex() {
            return propWithoutIndex;
        }


        public String getPropWithIndex50() {
            return propWithIndex50;
        }

        public String getPropWithIndex50AsWell() {
            return propWithIndex50AsWell;
        }

        public boolean isBooleanProp() {
            return booleanProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public String getPropWithNegativeIndex() {
            return propWithNegativeIndex;
        }

        public String getPropWithoutColumnName() {
            return propWithoutColumnName;
        }
        /*generated getters */
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
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForBeans2Sheet(UnsupportedPropTypeBean.class, errors);
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


    @Test
    void getMappings_allCases() throws NoSuchFieldException {

        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForBeans2Sheet(ChildBean.class, errors);
        pacList.forEach(System.out::println);

        errors.forEach(System.err::println);

        assertEquals(new PropAndColumn("parentField", "Parent Field", 0), pacList.get(0));
        assertEquals(new PropAndColumn("booleanProp", "Boolean Property", 1), pacList.get(1));
        assertEquals(new PropAndColumn("stringProp", "String Property", 2), pacList.get(2));


        assertTrue(havingErrorContains(errors, "'propWithoutGetter' is not a readable property"));

        assertTrue(havingErrorContains(errors, "Property Without Index", "The index should be provided"));

        assertTrue(havingErrorContains(errors, "Property With Negative Index", "-3"));


        assertEquals(new PropAndColumn("propWithoutColumnName", "Prop Without Column Name", 4), pacList.get(3));


        assertEquals(new PropAndColumn("propByGetter", "Property by Getter not by Field", 5), pacList.get(4));


        assertTrue(havingErrorContains(errors, "fakeGetter", "not a getter/setter"));

        assertEquals(new PropAndColumn("propBySetter", "Property by Setter not by Field", 7), pacList.get(5));


        assertTrue(havingErrorContains(errors, "fakeSetter", "not a getter/setter"));


        assertEquals(new PropAndColumn("propWithIndex50", "Property with index 50", 50), pacList.get(6));

        assertEquals(new PropAndColumn("propWithIndex50AsWell", "Property with index 50 As Well", 50), pacList.get(7));

        assertTrue(havingErrorContains(errors, "duplicated column indexes", "50"));

    }


}