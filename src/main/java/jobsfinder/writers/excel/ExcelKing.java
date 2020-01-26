package jobsfinder.writers.excel;

import com.sun.media.sound.InvalidFormatException;
import jobsfinder.writers.excel.ExcelRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelKing {
    public static void main(String[] args) {

    }

    public ExcelKing(String filename, String sheetName)  {
        this.workbookName = filename;
        setupWorkbook();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        IndexedColors headerTextColor = (headerTextColors.containsKey("testsheet") ?
                headerTextColors.get("testsheet") : defaultHeaderTextColor);
        headerFont.setColor(headerTextColor.getIndex());

        if (workbook.getSheet(sheetName)==null)  {
            workbook.createSheet(sheetName);
        }
    }

    private Workbook workbook;
    private File workbookFile;
    private String workbookName;
    private FileInputStream fileInputStream;

    //for styles
    private Map<String, IndexedColors> headerTextColors = new HashMap<>();
    private Map<String, IndexedColors> headerBackgroundColors = new HashMap<>();

    private Map<String, CellStyle> headerStyles = new HashMap<>();

    private IndexedColors defaultHeaderTextColor = IndexedColors.BLACK;
    private IndexedColors defaultHeaderBackgroundColor = IndexedColors.WHITE;

    private boolean workbookNew = false;

    public boolean isWorkbookNew()  {
        return workbookNew;
    }

    public void setupWorkbook()  {
        workbookFile = new File(workbookName);

        boolean createNewFile = true;
        if (workbookFile.exists() && workbookFile.isFile()) {
            try {
                fileInputStream = new FileInputStream(workbookFile);
                workbook = new XSSFWorkbook(fileInputStream);

                createNewFile = false;
            }
            catch (Exception e)  {
                e.printStackTrace();
                createNewFile = true;

            }
            finally  {
                try  {
                    fileInputStream.close();
                }
                catch (Exception e)  {
                    e.printStackTrace();
                }
            }
        }

        if (createNewFile)  {
            workbook = new XSSFWorkbook();
            workbookNew = true;
        }
    }

    public Workbook getWorkbook()  {return workbook;}

    public Sheet getSheet(String sheetName)  {
        return workbook.getSheet(sheetName);
    }

    private void cleanSheet(Sheet sheet) {
        int numberOfRows = sheet.getPhysicalNumberOfRows();

        if(numberOfRows > 0) {
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                if(sheet.getRow(i) != null) {
                    sheet.removeRow( sheet.getRow(i));
                } else {
                    //System.out.println("Info: clean sheet='" + sheet.getSheetName() + "' ... skip line: " + i);
                }
            }
        } else {
            //System.out.println("Info: clean sheet='" + sheet.getSheetName() + "' ... is empty");
        }
    }

    public boolean clearWorkbook()  {
        int sheetsNumber = workbook.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<>();
        for (int i=0; i<sheetsNumber; i++)  {
            cleanSheet(workbook.getSheetAt(i));
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(workbookName);
            workbook.write(fileOut);
            fileOut.close();
            //workbook.close();

            return true;
        }
        catch (Exception e)  {
            return false;
        }
    }

    public int getLastRow(String sheetName)  {
        int lastRowNum = workbook.getSheet(sheetName).getLastRowNum();

        if (lastRowNum == 0)  {
            return workbook.getSheet(sheetName).getPhysicalNumberOfRows() > 0 ? 0 : -1;
        }

        return lastRowNum;
    }

    public void append(List<ExcelRow> rows, String sheetName)  {
        int starRow = 1;

        if (workbook.getSheetIndex(sheetName) < 0)  {
            workbook.createSheet(sheetName);
        }

        try  {
            int lastRow = getLastRow(sheetName);
            //System.out.println("lastRow: " + lastRow);
            starRow = lastRow==-1 ? lastRow : lastRow + 1;

            write(rows,  sheetName, starRow);
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
    }

    public void write(List<ExcelRow> rows,
                      String sheetName) {
        try  {
            write(rows, sheetName, 0);
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
    }

    public void style(String sheetName, int height, int width)  {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet==null) {
            return;
        }

        CellStyle headerCellStyle;
        if (headerStyles.containsKey(sheetName))  {
            headerCellStyle = headerStyles.get(sheetName);
        }
        else  {
            headerCellStyle = workbook.createCellStyle();

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);

            IndexedColors headerTextColor = (headerTextColors.containsKey(sheetName) ?
                    headerTextColors.get(sheetName) : defaultHeaderTextColor);
            headerFont.setColor(headerTextColor.getIndex());

            headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            IndexedColors headerBackgroundColor = (headerBackgroundColors.containsKey(sheetName) ?
                    headerBackgroundColors.get(sheetName) : defaultHeaderBackgroundColor);
            headerCellStyle.setFillForegroundColor(headerBackgroundColor.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            headerStyles.put(sheetName, headerCellStyle);
        }

        Row headerRow = sheet.getRow(0);
        for (int w = 0; w < width; w++)  {
            Cell cell = headerRow.getCell(w);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public void setHeaderTextColorForSheet(String sheetName, IndexedColors color)  {
        headerTextColors.put(sheetName, color);
    }

    public void setHeaderBackgroundColorForSheet(String sheetName, IndexedColors color)  {
        headerBackgroundColors.put(sheetName, color);
    }

    public void write(List<ExcelRow> rows,
                      String sheetName, int startRow)
                                        throws IOException {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet==null) {
            sheet = workbook.createSheet(sheetName);
        }

        if (startRow==0)  {
            clearWorkbook();
        }

        int currentRow = startRow;
        for (int i = 0; i <rows.size(); i++)  {
            List<String> rowData = rows.get(i).getData();

            Row row = sheet.createRow(currentRow++);
            //row.setHeight((short) 2400);

            for (int j=0; j<rowData.size(); j++)  {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(j));
                cell.setCellStyle(rows.get(i).getStyle());
            }
        }

        System.out.println("finished editing");
        // Resize all columns to fit the content size
        /*int columnsCount = rows.size() == 1 ?
                    rows.get(0).getData().size() :
                    Math.max(rows.get(0).getData().size(), rows.get(1).getData().size());
        for(int z = 0; z <columnsCount; z++) {
            //sheet.autoSizeColumn(z);
            //sheet.setColumnWidth(z, Math.min(20000, sheet.getColumnWidth(z)));
        }*/

        System.out.println("finished resizing");
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(workbookName);
        workbook.write(fileOut);
        fileOut.close();

        System.out.println("closing");

        System.out.println("closed: " + workbookFile.getAbsolutePath());
    }
}

