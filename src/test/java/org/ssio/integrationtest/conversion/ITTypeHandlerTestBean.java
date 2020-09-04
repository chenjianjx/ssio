package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.common.annotation.SsColumn;
import org.ssio.api.common.typing.SsioComplexTypeHandler;

public class ITTypeHandlerTestBean {
    @SsColumn(index = 0, typeHandlerClass = NameTypeHandler.class)
    private Name name;

    @SsColumn(index = 1)
    private String nickName;

    /**
     * unit is cm , we be saved as "1.85m" in the sheet
     * a negative number means unknown
     */
    @SsColumn(index = 2, typeHandlerClass = HeightTypeHandler.class)
    private int height = -1;


    public static ITTypeHandlerTestBean beckham() {
        ITTypeHandlerTestBean beckham = new ITTypeHandlerTestBean();
        beckham.setName(new Name("David", "Beckham"));
        beckham.setHeight(180);
        beckham.setNickName("Golden Balls");
        return beckham;
    }


    public static ITTypeHandlerTestBean nobody() {
        ITTypeHandlerTestBean beckham = new ITTypeHandlerTestBean();
        return beckham;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static class Name {
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Name() {
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    public static class NameTypeHandler implements SsioComplexTypeHandler<Name> {

        @Override
        public Class<?> getTargetSimpleType() {
            return String.class;
        }

        @Override
        public String toSimpleTypeValue(Name originalValue) {
            if (originalValue == null) {
                return null;
            }
            return originalValue.getFirstName() + " " + originalValue.getLastName();
        }

        @Override
        public Name fromSimpleTypeValue(Object simpleTypeValue) {
            if (simpleTypeValue == null) {
                return null;
            }
            String stv = (String) simpleTypeValue;
            Name name = new Name();
            name.setFirstName(StringUtils.split(stv)[0]);
            name.setLastName(StringUtils.split(stv)[1]);
            return name;
        }
    }

    public static class HeightTypeHandler implements SsioComplexTypeHandler<String> {

        @Override
        public Class<?> getTargetSimpleType() {
            return int.class;
        }

        @Override
        public Object toSimpleTypeValue(String originalValue) {
            if (originalValue == null) {
                return -1;
            }
            double meter = Double.parseDouble(originalValue.substring(0, originalValue.indexOf("cm")));
            return (int) (meter * 100);
        }

        @Override
        public String fromSimpleTypeValue(Object simpleTypeValue) {
            int stv = (int) simpleTypeValue;
            if (stv < 0) {
                return null;
            }
            return (((double) stv) / 100) + "cm";
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
