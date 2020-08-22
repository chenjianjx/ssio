package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.api.common.abstractsheet.model.office.OfficeCell;
import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;


public abstract class OfficeCellValueBinder implements SsCellValueBinder<OfficeCell> {


    @Override
    public void setNonNullValue(OfficeCell cell, Object value) {
        this.setNonNullValueToPoiCell(cell.getPoiCell(), value);
    }

    @Override
    public void setNullValue(OfficeCell cell) {
        cell.getPoiCell().setBlank();
    }

    @Override
    public Object getValue(OfficeCell cell) {
        return this.getValueFromPoiCell(cell.getPoiCell());
    }

    /**
     * set value to a cell
     *
     * @param poiCell
     * @param value   the java type of the value must be the curated type of the binder
     */
    protected abstract void setNonNullValueToPoiCell(Cell poiCell, Object value);

    /**
     * @param poiCell
     * @return the java type of the value will be the curated type of the binder
     */
    protected abstract Object getValueFromPoiCell(Cell poiCell);


    protected RuntimeException noSupportToReadFormulaCellException() {
        throw new RuntimeException("It's not supported yet to read value from a formula-typed cell");
    }

    protected RuntimeException cannotReadFormulaErrorCellException() {
        throw new RuntimeException("Cannot read value from a formula-error-typed cell");
    }

    protected RuntimeException numericValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read numeric value from a boolean-typed cell");
    }

    protected RuntimeException booleanValueFromNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read boolean value from a numeric-typed cell");
    }

    protected RuntimeException dateValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read date or date time value from a boolean-typed cell");
    }

    protected RuntimeException dateValueFromVanilaNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read date or date time value from a number-typed cell");
    }

    protected RuntimeException enumValueFromBooleanCellNotAllowedException() {
        throw new RuntimeException("Cannot read enum value from a boolean-typed cell");
    }

    protected RuntimeException enumValueFromNumericCellNotAllowedException() {
        throw new RuntimeException("Cannot read enum value from a numeric-typed cell");
    }
}
