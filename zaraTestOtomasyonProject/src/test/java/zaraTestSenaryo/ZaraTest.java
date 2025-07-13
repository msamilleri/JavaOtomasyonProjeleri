package zaraTestSenaryo;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utilities.ConfigReader;
import utilities.ExcelReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.FileUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ZaraTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(ZaraTest.class);
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private SeacrhProductPage seacrhProductPage;
    private ProductListingPage productListingPage;
    private ProductDetailPage productDetailPage;
    private  BasketPage basketPage;


    @BeforeEach
    public void setUp() {
        driver = setupDriver(); // BaseTest'ten gelen yöntem
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get(ConfigReader.getProperty("base.url"));
        homePage = new HomePage(driver);
        seacrhProductPage = new SeacrhProductPage(driver); // ← BU SATIR EKSİKSE HATA VERİR
        logger.info("Tarayıcı başlatıldı ve Zara sitesi açıldı");

        removeAutomationFlags();
    }
    private void removeAutomationFlags() {
        // Webdriver özelliğini gizle
        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
        );

        // Diğer tarayıcı izlerini temizle
        ((JavascriptExecutor) driver).executeScript(
                "const originalProto = Object.getPrototypeOf(navigator);" +
                        "Object.defineProperty(navigator, 'plugins', {get: () => [1, 2, 3]});" +
                        "Object.defineProperty(navigator, 'languages', {get: () => ['tr-TR', 'tr']});" +
                        "Object.defineProperty(originalProto, 'platform', {get: () => 'Win32'});" +
                        "Object.defineProperty(originalProto, 'doNotTrack', {get: () => '1'});"
        );


    }


    @Test
    public void testZaraShoppingFlow() throws InterruptedException {

        // 1. Önce Giriş Yap sayfasına gidilir
        homePage.rejectCookies();
        homePage.goToLoginPage();
        homePage.goToLoginPageFirst();
        loginPage = new LoginPage(driver);
        loginPage.loginWebSite(
                ConfigReader.getProperty("user.email"),
                ConfigReader.getProperty("user.password")
        );
        logger.info("Kullanıcı girişi yapıldı");

        // 2. Menüye tıklanır -> Erkek –> Tümünü Gör butonlarına tıklanır
        homePage.navigateToMenCategory();
        logger.info("Erkek kategorisine gidildi");
        Thread.sleep(3000);
        homePage.clickSearchButton();
        Thread.sleep(3000);

        // 3. Excel'den arama terimlerini al
        String sortKelimes = ExcelReader.readCellData(0, 0); // şort
        String gomlekKelimesi = ExcelReader.readCellData(0, 1); // gömlek

        // 4. Arama işlemleri
        seacrhProductPage.searchForTerm(sortKelimes);
        Thread.sleep(1000);
        seacrhProductPage.removeShortFromSearch();
        Thread.sleep(1000);
        seacrhProductPage.searchForTerm(gomlekKelimesi);
        seacrhProductPage.enterSearch();
        Thread.sleep(1000);
        logger.info("Arama işlemleri tamamlandı");

        // 5. Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.
        productListingPage = new ProductListingPage(driver);
        productListingPage.selectRandomProduct();
        logger.info("Rastgele ürün seçildi");

        // 6. Ürün detayları
        productDetailPage = new ProductDetailPage(driver);
        String productName = productDetailPage.getProductName();
        String productPrice = productDetailPage.getProductPrice();


        // 7. Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazılır.
        FileUtils.saveProductInfo("product_info.txt", productName, productPrice);
        logger.info("Ürün bilgileri kaydedildi");

        // 8. Seçilen ürün sepete eklenir.
        productDetailPage.addToCart();
        productDetailPage.selectFirstAvailableSize();
        productDetailPage.goToCart();
        logger.info("Ürün sepete eklendi");

        // 9. Fiyat karşılaştırma
        basketPage = new BasketPage(driver);
        String cartPrice = basketPage.getCartItemPrice();
        Assertions.assertEquals(productPrice, cartPrice, "Fiyatlar eşleşmiyor");
        logger.info("Fiyat doğrulaması yapıldı");

        // 10. Adet arttırma
        basketPage.increaseQuantity();
        Thread.sleep(2000);
        Assertions.assertEquals("2", basketPage.getQuantity(), "Adet 2 olmalı");
        logger.info("Adet arttırıldı ve doğrulandı");

        // 11. Ürünü sil
        basketPage.removeItem();
        Thread.sleep(1000);
        Assertions.assertTrue(basketPage.isCartEmpty(), "Sepet boş değil");
        logger.info("Ürün sepetten silindi ve sepet boşluğu doğrulandı");


    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Tarayıcı kapatıldı");
        }
    }
}
