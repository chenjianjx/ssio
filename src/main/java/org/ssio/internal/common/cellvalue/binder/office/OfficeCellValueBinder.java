package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Specify how to read a value from office cell and how to write a value to an office cell, according to the java type
 * Note: the implementations may be stateful (non-thread safe)
 */
public interface OfficeCellValueBinder {

    /**
     * set value to a cell
     *
     * @param cell
     * @param value the java type of the value must be the curated type of the binder
     */
    void setNonNullValue(Cell cell, Object value);

    /**
     * @param poiCell
     * @return the java type of the value will be the curated type of the binder
     */
    Object getValue(Cell poiCell);


    default RuntimeException primitiveValueFromEmptyCellNotAllowedException() {
        throw new RuntimeException("Cannot read primitive value (boolean, int, double etc.) from an empty cell");
    }

    default RuntimeException noSupportToReadFormulaCellException() {
        throw new RuntimeException("It's not supported yet to read value from a formula-typed cell");
    }

    default RuntimeException cannotReadFormulaErrorCellException() {
        throw new RuntimeException("Cannot read value from a formula-error-typed cell");
    }

    default RuntimeException numericValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read numeric value from a boolean-typed cell");
    }

    default RuntimeException booleanValueFromNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read boolean value from a numeric-typed cell");
    }

    default RuntimeException dateValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read date or date time value from a boolean-typed cell");
    }

    default RuntimeException dateValueFromVanilaNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read date or date time value from a number-typed cell");
    }

    default RuntimeException enumValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read enum value from a boolean-typed cell");
    }

    default RuntimeException enumValueFromNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read enum value from a numeric-typed cell");
    }

}
