package pages;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@class='zds-input-base__input email-input__input-base']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@class='zds-input-base__input password-input__input-base']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='zds-button generic-form-submit-button login-form__submit-button zds-button--primary zds-button--small']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginWebSite(String email, String password) {
        sendKeys(emailInput, email);
        sendKeys(passwordInput, password);
        clickElement(loginButton);
    }
}
