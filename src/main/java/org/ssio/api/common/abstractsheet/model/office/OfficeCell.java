package org.ssio.api.common.abstractsheet.model.office;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsCellValueJavaType;
import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinderRepo;

public class OfficeCell implements SsCell {
    private Cell poiCell;

    private OfficeCell() {

    }

    public static OfficeCell createFromExistingPoiCell(Cell poiCell) {
        OfficeCell cell = new OfficeCell();
        cell.poiCell = poiCell;
        return cell;
    }

    public static OfficeCell createEmptyCell(Row poiRow, int columnIndex) {
        Cell poiCell = poiRow.createCell(columnIndex);
        OfficeCell cell = new OfficeCell();
        cell.poiCell = poiCell;
        return cell;
    }

    public Cell getPoiCell() {
        return poiCell;
    }


    @Override
    public SsCellValueBinder getCellValueBinder(SsCellValueJavaType javaType, Class<Enum<?>> enumClassIfEnum) {
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