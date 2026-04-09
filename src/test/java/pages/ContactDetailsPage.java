package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class ContactDetailsPage extends CommonMethods {
    @FindBy(xpath = "//a[normalize-space()='Contact Details']")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//label[text()='Street 1']/../following-sibling::div/input")
    public WebElement addressStreet1Field;

    @FindBy(xpath = "//label[text()='Street 2']/../following-sibling::div/input")
    public WebElement addressStreet2Field;

    @FindBy(xpath = "//label[text()='City']/../following-sibling::div/input")
    public WebElement cityField;

    @FindBy(xpath = "//label[text()='State/Province']/../following-sibling::div/input")
    public WebElement stateField;

    @FindBy(xpath = "//label[text()='Zip/Postal Code']/../following-sibling::div/input")
    public WebElement zipCodeField;

    @FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--active']")
    public WebElement countryDropdown;

    @FindBy(xpath = "//label[text()='Home']/../following-sibling::div/input")
    public WebElement homePhoneField;

    @FindBy(xpath = "//label[text()='Mobile']/../following-sibling::div/input")
    public WebElement mobilePhoneField;

    @FindBy(xpath = "//label[text()='Work']/../following-sibling::div/input")
    public WebElement workPhoneField;

    @FindBy(xpath = "//label[text()='Work Email']/../following-sibling::div/input")
    public WebElement workEmailField;

    @FindBy(xpath = "//label[text()='Other Email']/../following-sibling::div/input")
    public WebElement otherEmailField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;

    public ContactDetailsPage() {
        PageFactory.initElements(driver, this);
    }
}
