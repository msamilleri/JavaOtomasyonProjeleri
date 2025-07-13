package utilities;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void saveProductInfo(String fileName, String productName, String productPrice) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/java/testdata/" + fileName))) {
            writer.write("Ürün Adı: " + productName);
            writer.newLine();
            writer.write("Fiyat: " + productPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}