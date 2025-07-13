package pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    @FindBy(partialLinkText = "GİRİŞ YAP")
    private WebElement loginLink;

    @FindBy(xpath = "//button[@aria-label='Menüyü aç']")
    private WebElement menuButton;

    @FindBy(partialLinkText = "ERKEK")
    private WebElement menCategory;

    @FindBy(xpath = "//a[contains(@href, 'l10847')]")
    private WebElement viewAllProducts;

    @FindBy(xpath = "//span[@class='layout-header-action-search__content']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@data-qa-id='oauth-logon-button']")
    private WebElement loginButtonFirst;

    @FindBy(id = "onetrust-reject-all-handler")
    private WebElement rejectBtn;

    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    private WebElement acceptBtn;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToLoginPageFirst() {
        clickElement(loginButtonFirst);
    }
    public void goToLoginPage() {
        clickElement(loginLink);
    }

    public void navigateToMenCategory() {
        clickElement(menuButton);
        clickElement(menCategory);
        clickElement(viewAllProducts);
    }

    public void clickSearchButton() {
        clickElement(searchButton);
        waitUntilVisible(By.xpath ( "//span[@class='layout-header-action-search__content']"));

    }


    public void rejectCookies() {
        try {
            clickElement(rejectBtn);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Çerez kutusu çıkmadı!");
        }
    }

    public void acceptCookies() {
        try {
            clickElement(acceptBtn);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Çerez kutusu çıkmadı!");
        }
    }



}