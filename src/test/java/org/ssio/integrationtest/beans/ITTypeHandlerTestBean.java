package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.typing.ComplexTypeHandler;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class ITTypeHandlerTestBean {
    @SsColumn(index = 0, typeHandler = NameTypeHandler.class)
    private Name name;

    @SsColumn(index = 1)
    private String nickName;

    @SsColumn(index = 2, typeHandler = BirthDateTypeHandler.class)
    private LocalDate birthDate;


    public static ITTypeHandlerTestBean beckham() {
        ITTypeHandlerTestBean beckham = new ITTypeHandlerTestBean();
        beckham.setName(new Name("David", "Beckham"));
        beckham.setNickName("Golden Balls");
        beckham.setBirthDate(LocalDate.of(1975, 5, 2));
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


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name = (Name) o;
            return Objects.equals(firstName, name.firstName) &&
                    Objects.equals(lastName, name.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }


    }

    public static class NameTypeHandler implements ComplexTypeHandler<Name, String> {

        @Override
        public Class<String> getTargetSimpleType() {
            return String.class;
        }

        @Override
        public String nonNullValueToSimple(Name complexTypeValue) {

            return complexTypeValue.getFirstName() + " " + complexTypeValue.getLastName();
        }

        @Override
        public String nullValueToSimple() {
            return null;
        }

        @Override
        public Name fromNonNullSimpleTypeValue(String simpleTypeValue) {
            Name name = new Name();
            name.setFirstName(StringUtils.split(simpleTypeValue)[0]);
            name.setLastName(StringUtils.split(simpleTypeValue)[1]);
            return name;
        }

        @Override
        public Name fromNullSimpleTypeValue() {
            return null;
        }
    }

    public static class BirthDateTypeHandler implements ComplexTypeHandler<LocalDate, Long> {

        @Override
        public Class<Long> getTargetSimpleType() {
            return Long.class;
        }

        @Override
        public Long nonNullValueToSimple(LocalDate complexTypeValue) {
            return complexTypeValue.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        }

        @Override
        public Long nullValueToSimple() {
            return null;
        }

        @Override
        public LocalDate fromNonNullSimpleTypeValue(Long simpleTypeValue) {
            return
                    Instant.ofEpochMilli(simpleTypeValue)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate();
        }

        @Override
        public LocalDate fromNullSimpleTypeValue() {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITTypeHandlerTestBean that = (ITTypeHandlerTestBean) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nickName, birthDate);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
