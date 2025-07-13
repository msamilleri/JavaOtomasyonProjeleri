package zaraTestSenaryo;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Collections;

public class BaseTest {
    protected WebDriver driver;

    protected WebDriver setupDriver() {
        // ChromeOptions'ı yapılandır
        ChromeOptions options = new ChromeOptions();

        // Bot tespitini önleyen temel ayarlar
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Gelişmiş gizlilik ayarları
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");

        // Pencerenin boyutunu ayarla
        options.addArguments("--window-size=1920,1080");

        // Kullanıcı ajanını değiştir (güncel bir ajan kullanın)
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");

        // WebDriver'ı başlat
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

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

        return driver;
    }
}
