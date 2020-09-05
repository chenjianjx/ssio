package org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.model.OfficeCell;


public abstract class OfficeCellValueBinder implements SsCellValueBinder<OfficeCell> {


    @Override
    public void setNonNullValue(OfficeCell cell, String format, Object value) {
        this.setNonNullValueToPoiCell(cell.getPoiCell(), format, value);
    }

    @Override
    public void setNullValue(OfficeCell cell) {
        cell.getPoiCell().setBlank();
    }

    @Override
    public Object getValue(OfficeCell cell, String format) {
        if (cell.getPoiCell() == null) {
            return null;
        }
        return this.getValueFromPoiCell(cell.getPoiCell(), format);
    }

    /**
     * set value to a cell
     *
     * @param poiCell
     * @param format
     * @param value   the java type of the value must be the curated type of the binder
     */
    protected abstract void setNonNullValueToPoiCell(Cell poiCell, String format, Object value);

    /**
     * @param poiCell not null
     * @param format
     * @return the java type of the value will be the curated type of the binder
     */
    protected abstract Object getValueFromPoiCell(Cell poiCell, String format);


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
