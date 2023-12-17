package org.ssio.spi.impl.office.model.write;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.office.cellvaluebinder.OfficeCellValueBinderRepo;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;
import org.ssio.spi.interfaces.model.write.WritableCell;

public class OfficeWritableCell implements WritableCell {
    /**
     * note: can be null
     */
    private Cell poiCell;

    private OfficeWritableCell() {

    }

    public static OfficeWritableCell createEmptyCell(Row poiRow, int columnIndex) {
        Cell poiCell = poiRow.createCell(columnIndex);
        OfficeWritableCell cell = new OfficeWritableCell();
        cell.poiCell = poiCell;
        return cell;
    }

    public Cell getPoiCell() {
        return poiCell;
    }


    @Override
    public CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        return OfficeCellValueBinderRepo.getOfficeCellValueBinder(javaType, enumClassIfEnum);
    }

    @Override
    public void styleAsError() {
        CellStyle errStyle = poiCell.getRow().getSheet().getWorkbook().createCellStyle();
        errStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        errStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        poiCell.setCellStyle(errStyle);
    }

    @Override
    public void styleAsHeader() {
        CellStyle style = poiCell.getRow().getSheet().getWorkbook().createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        poiCell.setCellStyle(style);
    }

}
