package utilities;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;


public class ExcelReader {
    public static String readCellData(int rowNum, int colNum) {
        try (FileInputStream file = new FileInputStream("src/test/java/testdata/urun_listesi.xlsx");
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            return cell.getStringCellValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}