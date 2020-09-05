package org.ssio.api.internal.common;

import org.junit.jupiter.api.Test;
import org.ssio.api.external.annotation.SsColumn;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ssio.testutil.SsioTestUtils.havingErrorContains;

class BeanClassInspectorTest_Save {

    BeanClassInspector inspector = new BeanClassInspector();


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
    void getMappings_allCases() throws NoSuchFieldException {

        List<String> errors = new ArrayList<>();
        List<PropAndColumn> pacList = inspector.getPropAndColumnMappingsForSaveMode(ChildBean.class, errors);
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