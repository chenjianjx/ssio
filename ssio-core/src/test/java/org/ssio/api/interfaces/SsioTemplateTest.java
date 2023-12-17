package org.ssio.api.interfaces;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssio.api.interfaces.annotation.SsColumn;
import org.ssio.api.interfaces.parse.CellError;
import org.ssio.api.interfaces.parse.CellsErrorDuringParseException;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.api.interfaces.save.DataErrorDuringSaveException;
import org.ssio.api.interfaces.save.DatumError;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SsioTemplateTest {

    public static final class TestBean {
        @SsColumn(index = 0, name = "String")
        private String str;

        public TestBean(String str) {
            this.str = str;
        }

        public TestBean() {

        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            TestBean that = (TestBean) o;

            return new EqualsBuilder().append(str, that.str).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(str).toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("str", str)
                    .toString();
        }
    }

    private static class TestSsioTemplate extends AbstractSsioTemplate {

        private SsioManager ssioManager;

        @Override
        protected SsioManager getSsioManager() {
            return ssioManager;
        }
    }

    private static class TestSaveParamBuilder extends SaveParamBuilder {

        @Override
        protected void fileTypeSpecificValidate(List errors) {

        }

        @Override
        protected SaveParam fileTypeSpecificBuild(Collection collection, Class aClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
            return null;
        }
    }

    private static class TestParseParamBuilder extends ParseParamBuilder {

        @Override
        protected ParseParam fileTypeSpecificBuild(Class aClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
            return null;
        }

        @Override
        protected void fileTypeSpecificValidate(List errors) {

        }
    }


    private TestSsioTemplate ssioTemplate;

    @BeforeEach
    public void setup() {
        ssioTemplate = new TestSsioTemplate();
        ssioTemplate.ssioManager = mock(SsioManager.class);
    }


    static Stream<Arguments> save_provider() {
        return Stream.of(
                Arguments.of(true, Collections.singletonList(new DatumError())),
                Arguments.of(true, Collections.emptyList()),
                Arguments.of(false, Collections.singletonList(new DatumError())),
                Arguments.of(false, Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource("save_provider")
    void save(boolean stillSaveIfDataError, List<DatumError> datumErrorsDuringSave) throws Throwable {
        List<TestBean> beans = new ArrayList<>();
        ByteArrayOutputStream outputTarget = new ByteArrayOutputStream();

        SaveResult saveResult = new SaveResult();
        saveResult.getDatumErrors().addAll(datumErrorsDuringSave);
        when(ssioTemplate.ssioManager.save(any())).thenReturn(saveResult);

        Executable exec = () -> ssioTemplate.save(() -> new TestSaveParamBuilder(), beans, TestBean.class, outputTarget, stillSaveIfDataError);

        if (stillSaveIfDataError && datumErrorsDuringSave.size() > 0) {
            exec.execute();//success
        } else if (stillSaveIfDataError && datumErrorsDuringSave.size() == 0) {
            exec.execute();//success
        } else if (!stillSaveIfDataError && datumErrorsDuringSave.size() > 0) {
            assertThrows(DataErrorDuringSaveException.class, exec);
        } else if (!stillSaveIfDataError && datumErrorsDuringSave.size() == 0) {
            exec.execute();//success
        }
    }


    static Stream<Arguments> parse_provider() {
        return Stream.of(
                Arguments.of(true, Collections.singletonList(new CellError())),
                Arguments.of(true, Collections.emptyList()),
                Arguments.of(false, Collections.singletonList(new CellError())),
                Arguments.of(false, Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource("parse_provider")
    void parse(boolean throwIfCellErrors, List<CellError> cellErrorsDuringParse) throws IOException, CellsErrorDuringParseException {
        ByteArrayInputStream input = new ByteArrayInputStream(new byte[0]);

        ParseResult parseResult = new ParseResult<>();
        List<TestBean> resultBeans = Collections.singletonList(new TestBean("someString"));
        parseResult.getBeans().addAll(resultBeans);
        parseResult.getCellErrors().addAll(cellErrorsDuringParse);
        when(ssioTemplate.ssioManager.parse(any())).thenReturn(parseResult);

        Supplier<List<TestBean>> parse = () -> {
            try {
                return ssioTemplate.parse(() -> new TestParseParamBuilder(), input, TestBean.class, throwIfCellErrors);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        if (throwIfCellErrors && cellErrorsDuringParse.size() > 0) {
            assertThrows(CellsErrorDuringParseException.class, () -> parse.get());
        } else if (throwIfCellErrors && cellErrorsDuringParse.size() == 0) {
            assertArrayEquals(resultBeans.toArray(), parse.get().toArray());
        } else if (!throwIfCellErrors && cellErrorsDuringParse.size() > 0) {
            assertArrayEquals(resultBeans.toArray(), parse.get().toArray());
        } else if (!throwIfCellErrors && cellErrorsDuringParse.size() == 0) {
            assertArrayEquals(resultBeans.toArray(), parse.get().toArray());
        }
    }


}