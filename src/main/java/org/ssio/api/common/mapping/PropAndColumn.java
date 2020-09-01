package org.ssio.api.common.mapping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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


    public PropAndColumn() {
    }

    public PropAndColumn(String propName, String columnName, int columnIndex) {
        this.propName = propName;
        this.columnName = columnName;
        this.columnIndex = columnIndex;
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
