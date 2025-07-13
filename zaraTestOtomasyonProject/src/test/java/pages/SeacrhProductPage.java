package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeacrhProductPage extends BasePage {

    @FindBy (xpath="//input[@id='search-home-form-combo-input']")
    private WebElement searchTextArea;


    public SeacrhProductPage(WebDriver driver) {
        super(driver);
    }

    public void searchForTerm(String term) {
        waitUntilVisible(By.xpath( "//input[@id='search-home-form-combo-input']"));
        sendKeys(searchTextArea, term);
    }
    public void clickSearchButton() {
        clickElement(searchTextArea);
    }
    public void clearSearch() {
        searchTextArea.sendKeys(Keys.DELETE);
        //deleteInputField(searchTextArea);
    }

    public void enterSearch() {
        searchTextArea.sendKeys(Keys.ENTER);
    }


}
