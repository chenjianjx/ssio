package org.ssio.integrationtest;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.SsioManager;
import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetParamBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

class SsioManagerITCase {
    SsioManager manager = new SsioManager();

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
        manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        FileUtils.writeByteArrayToFile(createOfficeFile("beansToSheet_officeSanityTest"), spreadsheet);


    }

    private static class ITBean {

        private short primShort;
        private int primInt;
        private long primLong;
        private float primFloat;
        private double primDouble;
        private boolean primBoolean;

        private Short objShort;
        private Integer objInt;
        private Long objLong;
        private Float objFloat;
        private Double objDouble;
        private Boolean objBoolean;

        private BigInteger bigInteger;
        private BigDecimal bigDecimal;

        private String str;
        private Date date;


        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }


        public short getPrimShort() {
            return primShort;
        }

        public void setPrimShort(short primShort) {
            this.primShort = primShort;
        }

        public int getPrimInt() {
            return primInt;
        }

        public void setPrimInt(int primInt) {
            this.primInt = primInt;
        }

        public long getPrimLong() {
            return primLong;
        }

        public void setPrimLong(long primLong) {
            this.primLong = primLong;
        }

        public float getPrimFloat() {
            return primFloat;
        }

        public void setPrimFloat(float primFloat) {
            this.primFloat = primFloat;
        }

        public double getPrimDouble() {
            return primDouble;
        }

        public void setPrimDouble(double primDouble) {
            this.primDouble = primDouble;
        }

        public boolean isPrimBoolean() {
            return primBoolean;
        }

        public void setPrimBoolean(boolean primBoolean) {
            this.primBoolean = primBoolean;
        }

        public Short getObjShort() {
            return objShort;
        }

        public void setObjShort(Short objShort) {
            this.objShort = objShort;
        }

        public Integer getObjInt() {
            return objInt;
        }

        public void setObjInt(Integer objInt) {
            this.objInt = objInt;
        }

        public Long getObjLong() {
            return objLong;
        }

        public void setObjLong(Long objLong) {
            this.objLong = objLong;
        }

        public Float getObjFloat() {
            return objFloat;
        }

        public void setObjFloat(Float objFloat) {
            this.objFloat = objFloat;
        }

        public Double getObjDouble() {
            return objDouble;
        }

        public void setObjDouble(Double objDouble) {
            this.objDouble = objDouble;
        }

        public Boolean getObjBoolean() {
            return objBoolean;
        }

        public void setObjBoolean(Boolean objBoolean) {
            this.objBoolean = objBoolean;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

        public BigInteger getBigInteger() {
            return bigInteger;
        }

        public void setBigInteger(BigInteger bigInteger) {
            this.bigInteger = bigInteger;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
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