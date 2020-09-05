package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.ssio.api.external.annotation.SsColumn;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ITFormatTestBean {


    private static final String AMERICAN_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss";
    private static final String BRITISH_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss";
    private static final String OTHER_DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

    private static final String AMERICAN_DATE_PATTERN = "MM/dd/yyyy";
    private static final String BRITISH_DATE_PATTERN = "MM/dd/yyyy";
    private static final String OTHER_DATE_PATTERN = "yyyy/MM/dd";

    @SsColumn(index = 0, format = AMERICAN_DATE_TIME_PATTERN)
    private Date americanDate;

    @SsColumn(index = 1, format = BRITISH_DATE_TIME_PATTERN)
    private Date britishDate;

    @SsColumn(index = 2, format = OTHER_DATE_TIME_PATTERN)
    private Date otherDate;

    @SsColumn(index = 3, format = AMERICAN_DATE_PATTERN)
    private LocalDate americanLocalDate;

    @SsColumn(index = 4, format = BRITISH_DATE_PATTERN)
    private LocalDate britishLocalDate;

    @SsColumn(index = 5, format = OTHER_DATE_PATTERN)
    private LocalDate otherLocalDate;

    @SsColumn(index = 6, format = AMERICAN_DATE_TIME_PATTERN)
    private LocalDateTime americanLocalDateTime;

    @SsColumn(index = 7, format = BRITISH_DATE_TIME_PATTERN)
    private LocalDateTime britishLocalDateTime;

    @SsColumn(index = 8, format = OTHER_DATE_TIME_PATTERN)
    private LocalDateTime otherLocalDateTime;

    @SsColumn(index = 9, format = "invalid-but-should-be-ignored")
    private String notDateTyped;


    public Date getAmericanDate() {
        return americanDate;
    }

    public void setAmericanDate(Date americanDate) {
        this.americanDate = americanDate;
    }

    public Date getBritishDate() {
        return britishDate;
    }

    public void setBritishDate(Date britishDate) {
        this.britishDate = britishDate;
    }

    public Date getOtherDate() {
        return otherDate;
    }

    public void setOtherDate(Date otherDate) {
        this.otherDate = otherDate;
    }

    public LocalDate getAmericanLocalDate() {
        return americanLocalDate;
    }

    public void setAmericanLocalDate(LocalDate americanLocalDate) {
        this.americanLocalDate = americanLocalDate;
    }

    public LocalDate getBritishLocalDate() {
        return britishLocalDate;
    }

    public void setBritishLocalDate(LocalDate britishLocalDate) {
        this.britishLocalDate = britishLocalDate;
    }

    public LocalDate getOtherLocalDate() {
        return otherLocalDate;
    }

    public void setOtherLocalDate(LocalDate otherLocalDate) {
        this.otherLocalDate = otherLocalDate;
    }

    public LocalDateTime getAmericanLocalDateTime() {
        return americanLocalDateTime;
    }

    public void setAmericanLocalDateTime(LocalDateTime americanLocalDateTime) {
        this.americanLocalDateTime = americanLocalDateTime;
    }

    public LocalDateTime getBritishLocalDateTime() {
        return britishLocalDateTime;
    }

    public void setBritishLocalDateTime(LocalDateTime britishLocalDateTime) {
        this.britishLocalDateTime = britishLocalDateTime;
    }

    public LocalDateTime getOtherLocalDateTime() {
        return otherLocalDateTime;
    }

    public void setOtherLocalDateTime(LocalDateTime otherLocalDateTime) {
        this.otherLocalDateTime = otherLocalDateTime;
    }

    public String getNotDateTyped() {
        return notDateTyped;
    }

    public void setNotDateTyped(String notDateTyped) {
        this.notDateTyped = notDateTyped;
    }


    public static ITFormatTestBean firstDayOfEveryMonthIn2020() {
        ITFormatTestBean bean = new ITFormatTestBean();

        try {
            bean.setAmericanDate(DateUtils.parseDate("2020/01/01 00:00:00", OTHER_DATE_TIME_PATTERN));
            bean.setBritishDate(DateUtils.parseDate("2020/02/01 00:00:00", OTHER_DATE_TIME_PATTERN));
            bean.setOtherDate(DateUtils.parseDate("2020/03/01 00:00:00", OTHER_DATE_TIME_PATTERN));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        bean.setAmericanLocalDate(LocalDate.of(2020, 4, 1));
        bean.setBritishLocalDate(LocalDate.of(2020, 5, 1));
        bean.setOtherLocalDate(LocalDate.of(2020, 6, 1));

        bean.setAmericanLocalDateTime(LocalDateTime.of(2020, 7, 1, 0, 0, 0));
        bean.setBritishLocalDateTime(LocalDateTime.of(2020, 8, 1, 0, 0, 0));
        bean.setOtherLocalDateTime(LocalDateTime.of(2020, 9, 1, 0, 0, 0));

        bean.setNotDateTyped("I don't have a clue");
        return bean;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITFormatTestBean that = (ITFormatTestBean) o;
        return americanDate.equals(that.americanDate) &&
                britishDate.equals(that.britishDate) &&
                otherDate.equals(that.otherDate) &&
                americanLocalDate.equals(that.americanLocalDate) &&
                britishLocalDate.equals(that.britishLocalDate) &&
                otherLocalDate.equals(that.otherLocalDate) &&
                americanLocalDateTime.equals(that.americanLocalDateTime) &&
                britishLocalDateTime.equals(that.britishLocalDateTime) &&
                otherLocalDateTime.equals(that.otherLocalDateTime) &&
                notDateTyped.equals(that.notDateTyped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(americanDate, britishDate, otherDate, americanLocalDate, britishLocalDate, otherLocalDate, americanLocalDateTime, britishLocalDateTime, otherLocalDateTime, notDateTyped);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
