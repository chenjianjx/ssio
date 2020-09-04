package org.ssio.api.common.mapping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.common.typing.SsioComplexTypeHandler;
import org.ssio.api.common.typing.SsioSimpleTypeEnum;

import java.util.Objects;

public class PropAndColumn {
    /**
     * won't be null
     */
    private String propName;

    /**
     * won't be null
     */
    private String columnName;

    /**
     * 0-based
     */
    private int columnIndex;

    /**
     * a valid format or null
     */
    private String format;

    /**
     * a real handler or null
     */
    private Class<? extends SsioComplexTypeHandler> typeHandlerClass;

    /**
     * not null
     */
    private SsioSimpleTypeEnum simpleTypeEnum;

    /**
     * only provided if the property is an enum
     */
    private Class<Enum<?>> enumClassIfEnum;

    public PropAndColumn() {
    }

    public PropAndColumn(String propName, String columnName, int columnIndex) {
        this.propName = propName;
        this.columnName = columnName;
        this.columnIndex = columnIndex;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Class<? extends SsioComplexTypeHandler> getTypeHandlerClass() {
        return typeHandlerClass;
    }

    public void setTypeHandlerClass(Class<? extends SsioComplexTypeHandler> typeHandlerClass) {
        this.typeHandlerClass = typeHandlerClass;
    }

    public SsioSimpleTypeEnum getSimpleTypeEnum() {
        return simpleTypeEnum;
    }

    public void setSimpleTypeEnum(SsioSimpleTypeEnum simpleTypeEnum) {
        this.simpleTypeEnum = simpleTypeEnum;
    }

    public Class<Enum<?>> getEnumClassIfEnum() {
        return enumClassIfEnum;
    }

    public void setEnumClassIfEnum(Class<Enum<?>> enumClassIfEnum) {
        this.enumClassIfEnum = enumClassIfEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropAndColumn that = (PropAndColumn) o;
        return columnIndex == that.columnIndex &&
                propName.equals(that.propName) &&
                columnName.equals(that.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propName, columnName, columnIndex);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
