package pages;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
public class BasketPage  extends BasePage{



    @FindBy(xpath = "//span[@class='money-amount__main']")
    private WebElement cartItemPrice;

    @FindBy(xpath = "//svg[contains(@class,'zds-quantity-selector__icon')]")
    private WebElement addQuantityIcon;

    @FindBy(xpath = "//div[@class='zds-quantity-selector__decrease' and @data-qa-id='remove-order-item-unit']")
    private WebElement decreaseQuantityButton;


    @FindBy(xpath = "//input[@aria-label='PAMUKLU KETEN GÖMLEK miktarı']")
    private WebElement quantityValue;

    @FindBy(xpath = "//div[@class='zds-empty-state__title' and span[text()='SEPETİNİZ BOŞ']]")
    private WebElement sepetBosMesaji;


    @FindBy(css = "div.cart-empty-state")
    private WebElement emptyCartMessage;

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public String getCartItemPrice() {
        return getText(cartItemPrice);
    }

    public void increaseQuantity() {
        clickElement(addQuantityIcon);
    }

    public String getQuantity() {
        return getText(quantityValue);
    }

    public void removeItem() {
        clickElement(decreaseQuantityButton);
    }

    public boolean isCartEmpty() {
        try {
            return sepetBosMesaji.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
