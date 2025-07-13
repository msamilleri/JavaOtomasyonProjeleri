package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;

public class ProductListingPage extends BasePage {

    @FindBy(xpath = "//ul[@class='product-grid__product-list']")
    private WebElement productList;

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public void selectRandomProduct() {

        waitUntilVisible(By.xpath("//ul[@class='product-grid__product-list']/li"));

        List<WebElement> products = productList.findElements(By.tagName("li"));
        if (products.isEmpty()) {
            throw new RuntimeException("Ürün listesi boş!");
        }

        Random rand = new Random();
        WebElement selectedProduct = products.get(rand.nextInt(products.size()));
        clickElement(selectedProduct);
    }


}
