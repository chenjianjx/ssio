package org.ssio.api.internal.common;

import org.junit.jupiter.api.Test;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.parse.PropFromColumnMappingMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ssio.testutil.SsioTestUtils.havingErrorContains;

class BeanClassInspectorTest_Parse {

    BeanClassInspector inspector = new BeanClassInspector();


    public static class ParentBean {

        @SsColumn(index = 0, name = "Parent Field")
        private String parentField;

        private String propNotAnnotated;

        public void setParentField(String parentField) {
            this.parentField = parentField;
        }

        public void setPropNotAnnotated(String propNotAnnotated) {
            this.propNotAnnotated = propNotAnnotated;
        }
    }

    public static class ChildBean extends ParentBean {

        @SsColumn(index = 1, name = "Boolean Property")
        private boolean booleanProp;

        @SsColumn(index = 2, name = "String Property")
        private String stringProp;

        @SsColumn(index = 3, name = "Property Without Setter")
        private String propWithoutSetter;

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

        public void setPropByGetter(String s) {

        }

        @SsColumn(index = 6, name = "Fake Property by Getter not by Field")
        public String fakeGetter() {
            return null;
        }

        @SsColumn(index = 7, name = "Property by Setter not by Field")
        public void setPropBySetter(String s) {
        }


        @SsColumn(index = 8, name = "Fake Property by Setter not by Field")
        public void fakeSetter(String s) {

        }

        /*generated setters */

        public void setBooleanProp(boolean booleanProp) {
            this.booleanProp = booleanProp;
        }

        public void setStringProp(String stringProp) {
            this.stringProp = stringProp;
        }

        public void setPropWithoutIndex(String propWithoutIndex) {
            this.propWithoutIndex = propWithoutIndex;
        }

        public void setPropWithNegativeIndex(String propWithNegativeIndex) {
            this.propWithNegativeIndex = propWithNegativeIndex;
        }

        public void setPropWithoutColumnName(String propWithoutColumnName) {
            this.propWithoutColumnName = propWithoutColumnName;
        }


        /*generated setters */
    }


    @Test
    void getMappings_allCases_byIndex() {

        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForParseMode(ChildBean.class, PropFromColumnMappingMode.BY_INDEX, errors);
        pacList.forEach(System.out::println);

        errors.forEach(System.err::println);

        assertEquals(new PropAndColumn("parentField", "Parent Field", 0), pacList.get(0));
        assertEquals(new PropAndColumn("booleanProp", "Boolean Property", 1), pacList.get(1));
        assertEquals(new PropAndColumn("stringProp", "String Property", 2), pacList.get(2));


        assertTrue(havingErrorContains(errors, "'propWithoutSetter' is not a writable property"));

        assertTrue(havingErrorContains(errors, "Property Without Index", "The index should be provided"));

        assertTrue(havingErrorContains(errors, "Property With Negative Index", "-3"));


        assertEquals(new PropAndColumn("propWithoutColumnName", "Prop Without Column Name", 4), pacList.get(3));


        assertEquals(new PropAndColumn("propByGetter", "Property by Getter not by Field", 5), pacList.get(4));


        assertTrue(havingErrorContains(errors, "fakeGetter", "not a getter/setter"));

        assertEquals(new PropAndColumn("propBySetter", "Property by Setter not by Field", 7), pacList.get(5));


        assertTrue(havingErrorContains(errors, "fakeSetter", "not a getter/setter"));


    }


    @Test
    void getMappings_allCases_byName() {

        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForParseMode(ChildBean.class, PropFromColumnMappingMode.BY_NAME, errors);
        pacList.forEach(System.out::println);

        errors.forEach(System.err::println);


        assertTrue(pacList.contains(new PropAndColumn("parentField", "Parent Field", 0)));
        assertTrue(pacList.contains(new PropAndColumn("booleanProp", "Boolean Property", 1)));
        assertTrue(pacList.contains(new PropAndColumn("stringProp", "String Property", 2)));

        assertTrue(pacList.contains(new PropAndColumn("propWithoutIndex", "Property Without Index", SsColumn.INDEX_UNKNOWN)));
        assertTrue(pacList.contains(new PropAndColumn("propWithNegativeIndex", "Property With Negative Index", -3)));

        assertTrue(havingErrorContains(errors, "'propWithoutSetter' is not a writable property"));


        assertTrue(pacList.contains(new PropAndColumn("propWithoutColumnName", "Prop Without Column Name", 4)));
        assertTrue(pacList.contains(new PropAndColumn("propByGetter", "Property by Getter not by Field", 5)));


        assertTrue(havingErrorContains(errors, "fakeGetter", "not a getter/setter"));

        assertTrue(pacList.contains(new PropAndColumn("propBySetter", "Property by Setter not by Field", 7)));


        assertTrue(havingErrorContains(errors, "fakeSetter", "not a getter/setter"));


    }


}