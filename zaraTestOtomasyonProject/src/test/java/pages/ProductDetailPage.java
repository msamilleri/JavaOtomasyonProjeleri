package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProductDetailPage extends BasePage {
    @FindBy(css = "h1.product-detail-info__header-name")
    private WebElement productName;

    @FindBy(css = "span.money-amount__main")
    private WebElement productPrice;

    @FindBy(xpath = "//ul[contains(@class, 'size-list')]/li[not(contains(@class, 'is-disabled'))]")
    private List<WebElement> sizeOptions;


    @FindBy(xpath = "//button[span[text()='Ekle']]")
    private WebElement addToCartButton;

    @FindBy(css = "a[href*='/cart']")
    private WebElement viewCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public void selectFirstAvailableSize() {
        if (!sizeOptions.isEmpty()) {
            clickElement(sizeOptions.get(0));
        }
    }

    public void addToCart() {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // Butonun tıklanabilir olmasını bekle
            longWait.until(driver -> {
                try {
                    // Butonun görünür ve etkin olup olmadığını kontrol et
                    boolean isDisplayed = addToCartButton.isDisplayed();
                    boolean isEnabled = addToCartButton.isEnabled();

                    // Butonun "disabled" özelliğini kontrol et
                    String disabledAttr = addToCartButton.getAttribute("disabled");
                    boolean isDisabled = disabledAttr != null && disabledAttr.equals("true");

                    // Butonun CSS sınıflarını kontrol et (engellenmiş olabilir)
                    String classList = addToCartButton.getAttribute("class");
                    boolean isBlocked = classList.contains("disabled") || classList.contains("inactive");

                    return isDisplayed && isEnabled && !isDisabled && !isBlocked;
                } catch (StaleElementReferenceException e) {
                    // Element yenilenmiş, tekrar bul
                    addToCartButton = driver.findElement(
                            By.cssSelector("button[data-qa-action='open-size-selector']")
                    );
                    return false;
                }
            });

            // Butonu görünür alana getir
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});",
                    addToCartButton
            );

            // Animasyon için kısa bekleme
            Thread.sleep(500);

            // JavaScript ile tıkla (geleneksel click'ten daha güvenilir)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);



            // Başarılı ekleme onayını bekle
            waitForAddToCartConfirmation();

        } catch (Exception e) {

            throw new RuntimeException("Sepete ekleme işlemi başarısız oldu", e);
        }
    }

    private void waitForAddToCartConfirmation() {
        try {
            // Farklı onay elementlerini kontrol et
            List<By> confirmationSelectors = Arrays.asList(
                    By.cssSelector("div.add-to-cart-confirmation"),
                    By.xpath("//div[contains(text(), 'sepete eklendi')]"),
                    By.cssSelector("div[data-qa-action='add-to-cart-confirmation']")
            );

            for (By selector : confirmationSelectors) {
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.visibilityOfElementLocated(selector));

                    return;
                } catch (TimeoutException e) {
                    // Bu seçici çalışmadı, diğerini dene
                }
            }

        } catch (Exception e) {
            System.out.println("Onay beklenirken hata oluştu: " + e.getMessage());
        }
    }

    public void goToCart() {
        clickElement(viewCartButton);
    }
}