package org.ssio.integrationtest;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.SsioManager;
import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetParamBuilder;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.api.b2s.DatumError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;

class SsioManagerITCase {
    SsioManager manager = new SsioManager();

    @BeforeAll
    public static void init(){
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    @Test
    void beansToSheet_officeSanityTest() throws IOException {
        ITBean bean = new ITBean();

        Collection<ITBean> beans = Arrays.asList(bean);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ITBean> param =
                new BeansToSheetParamBuilder<ITBean>()
                        .setBeanClass(ITBean.class)
                        .setBeans(beans)
                        .setFileType(SpreadsheetFileType.OFFICE)
                        .setOutputTarget(outputStream)
                        .setSheetName("first sheet")
                        .build();


        // save it
       BeansToSheetResult result =  manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        FileUtils.writeByteArrayToFile(createOfficeFile("beansToSheet_officeSanityTest"), spreadsheet);

        if(result.hasDatumErrors()){
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }

    private enum ITEnum {
        ENUM_VALUE_A, ENUM_VALUE_B
    }

    public static class ITBean {

        private boolean primBoolean;
        private short primShort;
        private int primInt;
        private long primLong;
        private float primFloat;
        private double primDouble;


        private Boolean objBoolean;
        private Short objShort;
        private Integer objInt;
        private Long objLong;
        private Float objFloat;
        private Double objDouble;


        private BigInteger bigInteger;
        private BigDecimal bigDecimal;


        private Date date;
        private LocalDate localDate;
        private LocalDateTime localDateTime;


        private String str;
        private ITEnum enumeration;

        public boolean isPrimBoolean() {
            return primBoolean;
        }

        public short getPrimShort() {
            return primShort;
        }

        public int getPrimInt() {
            return primInt;
        }

        public long getPrimLong() {
            return primLong;
        }

        public float getPrimFloat() {
            return primFloat;
        }

        public double getPrimDouble() {
            return primDouble;
        }

        public Boolean getObjBoolean() {
            return objBoolean;
        }

        public Short getObjShort() {
            return objShort;
        }

        public Integer getObjInt() {
            return objInt;
        }

        public Long getObjLong() {
            return objLong;
        }

        public Float getObjFloat() {
            return objFloat;
        }

        public Double getObjDouble() {
            return objDouble;
        }

        public BigInteger getBigInteger() {
            return bigInteger;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public Date getDate() {
            return date;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public String getStr() {
            return str;
        }

        public ITEnum getEnumeration() {
            return enumeration;
        }
    }

    private File createOfficeFile(String prefix) {
        File dir = new File(System.getProperty("java.io.tmpdir"), "/ssio-it-test");
        dir.mkdirs();
        String filename = prefix + "-" + System.nanoTime() + ".xlsx";
        File file = new File(dir, filename);
        System.out.println("File created: " + file.getAbsolutePath());
        return file;
    }

}