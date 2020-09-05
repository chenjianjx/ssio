package org.ssio.integrationtest.fordoc;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.typing.ComplexTypeHandler;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Player {

    @SsColumn(index = 0, name = "Id")
    private long id;

    @SsColumn(index = 1) // the column name will be "Birth Country"
    private String birthCountry;

    @SsColumn(index = 2, typeHandler = FullNameTypeHandler.class) //composite type
    private FullName fullName;

    @SsColumn(index = 3) //A enum's name() will be saved. Otherwise, use a typeHandler
    private SportType sportType;

    @SsColumn(index = 4, format = "yyyy/MM/dd") //date format
    private LocalDate birthDate;

    @SsColumn(index = 5, typeHandler = TimestampAsMillisHandler.class)
    //if you prefer save timestamp as number
    private LocalDateTime createdWhen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(LocalDateTime createdWhen) {
        this.createdWhen = createdWhen;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class FullName {
        private String firstName;
        private String lastName;

        public FullName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public FullName() {
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

    public static class FullNameTypeHandler implements ComplexTypeHandler<FullName, String> {

        @Override
        public Class<String> getTargetSimpleType() {
            return String.class;
        }

        @Override
        public String nonNullValueToSimple(FullName complexTypeValue) {

            return complexTypeValue.getFirstName() + " " + complexTypeValue.getLastName();
        }

        @Override
        public String nullValueToSimple() {
            return null;
        }

        @Override
        public FullName fromNonNullSimpleTypeValue(String simpleTypeValue) {
            FullName name = new FullName();
            name.setFirstName(StringUtils.split(simpleTypeValue)[0]);
            name.setLastName(StringUtils.split(simpleTypeValue)[1]);
            return name;
        }

        @Override
        public FullName fromNullSimpleTypeValue() {
            return null;
        }
    }

    public static class TimestampAsMillisHandler implements ComplexTypeHandler<LocalDateTime, Long> {

        @Override
        public Class<Long> getTargetSimpleType() {
            return Long.class;
        }

        @Override
        public Long nonNullValueToSimple(LocalDateTime complexTypeValue) {
            return complexTypeValue.toInstant(ZoneOffset.UTC).toEpochMilli();
        }

        @Override
        public Long nullValueToSimple() {
            return null;
        }

        @Override
        public LocalDateTime fromNonNullSimpleTypeValue(Long simpleTypeValue) {
            return
                    Instant.ofEpochMilli(simpleTypeValue)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime();
        }

        @Override
        public LocalDateTime fromNullSimpleTypeValue() {
            return null;
        }
    }


    public enum SportType {
        FOOTBALL, BASKETBALL
    }


    public static Player beckham() {
        Player player = new Player();
        player.setId(System.currentTimeMillis());
        player.setBirthCountry("UK");
        player.setFullName(new FullName("David", "Beckham"));
        player.setSportType(SportType.FOOTBALL);
        player.setBirthDate(LocalDate.of(1975, 5, 2));
        player.setCreatedWhen(LocalDateTime.now());
        return player;
    }

    public static Player yaoming() {
        Player player = new Player();
        player.setId(System.currentTimeMillis());
        player.setBirthCountry("China");
        player.setFullName(new FullName("Ming", "Yao"));
        player.setSportType(SportType.BASKETBALL);
        player.setBirthDate(LocalDate.of(1980, 9, 12));
        player.setCreatedWhen(LocalDateTime.now());
        return player;
    }

}



