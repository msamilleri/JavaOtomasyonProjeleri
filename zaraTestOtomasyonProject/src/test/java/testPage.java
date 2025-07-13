import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class testPage {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.zara.com/tr/");


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement rejectBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("onetrust-reject-all-handler")
        ));
        rejectBtn.click();

        Thread.sleep(5000);




        WebElement aramacubu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='layout-header-action-search__content']")
        ));
        aramacubu.click();

        Thread.sleep(5000);




        WebElement aramaEkraniYaz = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='search-home-form-combo-input']")
        ));
        aramaEkraniYaz.sendKeys("şort");

        aramaEkraniYaz.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Hepsini seç
        aramaEkraniYaz.sendKeys(Keys.DELETE);                   // Sil

        Thread.sleep(5000);

        WebElement menuAC = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Menüyü aç']")
        ));
        menuAC.click();

        Thread.sleep(5000);

        WebElement erkekMEnu = wait.until(ExpectedConditions.elementToBeClickable(
                By.partialLinkText( "ERKEK")
        ));
        erkekMEnu.click();

        Thread.sleep(2000);


        WebElement tumunuGorBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'l10847')]")
        ));
        tumunuGorBtn.click();

        Thread.sleep(5000);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.partialLinkText("GİRİŞ YAP")
        ));
        loginBtn.click();


        Thread.sleep(2000);




        WebElement loginBTNN = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-qa-id='oauth-logon-button']")
        ));
        loginBTNN.click();

        Thread.sleep(5000);


        WebElement giris_alani = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@class='zds-input-base__input email-input__input-base']")
        ));
        giris_alani.sendKeys("mustafasamil.ileri@gmail.com");


        WebElement sifreAlani = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@class='zds-input-base__input password-input__input-base']")
        ));
        sifreAlani.sendKeys("Mstf19il.");


        WebElement girisYAp = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='zds-button generic-form-submit-button login-form__submit-button zds-button--primary zds-button--small']")
        ));
        girisYAp.click();

        Thread.sleep(5000);

        driver.close();

    }
}
