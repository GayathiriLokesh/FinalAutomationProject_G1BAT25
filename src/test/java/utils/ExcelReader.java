package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelReader {
    public static Map<String, String> readRow(String filePath, String sheetName, int rowNumber) {
        Map<String, String> rowData = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowNumber);

            if (headerRow == null || dataRow == null) {
                throw new RuntimeException("Invalid row number: " + rowNumber + " for sheet: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String key = formatter.formatCellValue(headerRow.getCell(i)).trim();
                String value = formatter.formatCellValue(dataRow.getCell(i)).trim();
                rowData.put(key, value);
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to read excel file: " + filePath, e);
        }

        return rowData;
    }
}
