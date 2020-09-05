package org.ssio.api.external.parse;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * To record the fact that some datum in the cell is wrong e.g. its value or type does not match the
 * header
 */
public class CellError implements Serializable {
    private static final long serialVersionUID = -6160610503938820467L;

    /**
     * the cell's rowIndex. 0-based
     */
    private int rowIndex;

    /**
     * the cell's columnIndex. 0-based
     */
    private int columnIndex;

    /**
     * the corresponding propName
     */
    private String propName;

    /**
     * the corresponding columnName
     */
    private String columnName;

    /**
     * the cause of the error. It could be null
     */
    private Exception cause;

    public int getRowIndex() {
        return rowIndex;
    }

    public int getRowIndexOneBased() {
        return getRowIndex() + 1;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getColumnIndexOneBased() {
        return getColumnIndex() + 1;
    }

    public void setColumnIndex(int columnIndex) {
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

    public Exception getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return MessageFormat.format("rowIndex = {0}, columnIndex = {1}, propName = \"{2}\", headerText = \"{3}\", cause = {4} ", rowIndex,
                columnIndex, propName, columnName, cause);
    }
}
