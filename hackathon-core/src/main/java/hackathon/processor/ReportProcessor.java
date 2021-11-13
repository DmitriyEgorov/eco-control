package hackathon.processor;

import hackathon.model.Manufacture;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportProcessor {
    private static final short COMMON_FONT_HEIGHT_PT = 9;
    private static final short BIG_FONT_HEIGHT_PT = 11;
    private static final String CALIBRI_FONT_NAME = "Calibri";
    private static final String DEFAULT_FONT_NAME = "Arial";
    private static final String TABLE_LABEL = "Отчет";
    private static final List<String> ROW_TITLES = Arrays.asList(
            "№",
            "Наименование",
            "Адрес",
            "ИНН",
            "Вид деятельности");
    private static final int ROW_NUMBER = ROW_TITLES.size() - 1;
    private static final int TITLES_ROWS = 2;

    public void buildSheet(SXSSFWorkbook workbook, List<Manufacture> manufactures) {
        SXSSFSheet sheet = createSheet(workbook);
        fillTopData(workbook, sheet);
        CellStyle cellStyle = getDataCellStyle(workbook);
        buildTitlesTable(sheet, cellStyle);
        fillTableData(workbook, sheet, manufactures);
    }

    private void fillTopData(Workbook wb, Sheet sheet) {
        CellStyle titleCellStyle = getTopCellStyle(wb);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(TABLE_LABEL);
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(),
                cell.getColumnIndex(), cell.getColumnIndex() + ROW_NUMBER));
        cell.setCellStyle(titleCellStyle);
    }

    private SXSSFSheet createSheet(SXSSFWorkbook workbook) {
        setWorkbookDefaultFont(workbook);
        return workbook.createSheet("report");
    }

    private void setWorkbookDefaultFont(Workbook wb) {
        Font defaultFont = wb.getFontAt((short) 0);
        defaultFont.setFontName(DEFAULT_FONT_NAME);
        defaultFont.setFontHeightInPoints(COMMON_FONT_HEIGHT_PT);
        defaultFont.setBold(false);
        defaultFont.setItalic(false);
    }

    private void buildTitlesTable(Sheet sheet, CellStyle cellStyle) {
        Row row = getNextLine(sheet);
        buildMergeTitles(row, cellStyle, ROW_TITLES);
    }

    private static Row getNextLine(Sheet sheet) {
        return sheet.createRow(getNextRowIndex(sheet));
    }

    private static int getNextRowIndex(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    private static void buildMergeTitles(Row row, CellStyle cellStyle, Iterable<String> titles) {
        AtomicInteger col = new AtomicInteger();
        titles.forEach(title -> {
            createCell(title, row, cellStyle, col.get());
            col.addAndGet(1);
        });
    }

    private static void createCell(String text, Row row, CellStyle cellStyle, int column) {
        Cell cell = row.createCell(column);
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);
    }

    private static CellStyle getDataCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(buildFont(wb, DEFAULT_FONT_NAME, COMMON_FONT_HEIGHT_PT, false));
        setBorderStyle(cellStyle, BorderStyle.HAIR);
        cellStyle.setWrapText(true);
        updateAlignmentStyle(cellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static Font buildFont(Workbook wb, String fontName, short height, boolean isBold) {
        Font font = wb.createFont();
        font.setBold(isBold);
        font.setFontName(fontName);
        font.setFontHeightInPoints(height);
        return font;
    }

    private CellStyle getTopCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(buildFont(wb, CALIBRI_FONT_NAME, BIG_FONT_HEIGHT_PT, false));
        updateAlignmentStyle(cellStyle, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static void updateAlignmentStyle(CellStyle cellStyle, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setVerticalAlignment(verticalAlignment);
    }

    private static void setBorderStyle(CellStyle cellStyle, BorderStyle style) {
        cellStyle.setBorderTop(style);
        cellStyle.setBorderBottom(style);
        cellStyle.setBorderRight(style);
        cellStyle.setBorderLeft(style);

    }

    private void fillTableData(Workbook wb, Sheet sheet, List<Manufacture> manufactures) {
        CellStyle cellStyle = getDataCellStyle(wb);
        for (Manufacture manufacture : manufactures) {
            Row row = getNextLine(sheet);
            fillTableRow(row, cellStyle, manufacture);
        }
    }


    private void fillTableRow(Row row, CellStyle cellStyle, Manufacture manufacture) {
        createCell(Integer.toString(row.getRowNum() + 1 - TITLES_ROWS), row, cellStyle, 0);
        fillTableNextCell(manufacture.getId(), row, cellStyle);
        fillTableNextCell(manufacture.getAddress(), row, cellStyle);
        fillTableNextCell(manufacture.getInn(), row, cellStyle);
        fillTableNextCell(manufacture.getType(), row, cellStyle);
    }

    private static void fillTableNextCell(String text, Row row, CellStyle cellStyle) {
        Cell cell = getNextCell(row);
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);
    }

    private static Cell getNextCell(Row row) {
        return row.createCell(row.getLastCellNum());
    }

}
