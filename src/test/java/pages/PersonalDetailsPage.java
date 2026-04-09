package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class PersonalDetailsPage extends CommonMethods {

    @FindBy(xpath = "//h6[text()='Personal Details']")
    public WebElement personalDetailsHeader;

    @FindBy(name = "firstName")
    public WebElement firstNameField;

    @FindBy(name = "middleName")
    public WebElement middleNameField;

    @FindBy(name = "lastName")
    public WebElement lastNameField;

    @FindBy(xpath = "//label[normalize-space()='Male']")
    public WebElement maleRadioButton;

    @FindBy(xpath = "//label[normalize-space()='Female']")
    public WebElement femaleRadioButton;

    @FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
    public WebElement employeeIdField;

    @FindBy(xpath = "//label[text()='Other Id']/../following-sibling::div/input")
    public WebElement otherIdField;

    @FindBy(xpath = "(//input[@placeholder='yyyy-mm-dd'])[1]")
    public WebElement licenseExpiryDateField;

    @FindBy(xpath = "//label[text()='Nationality']/following::div[contains(@class,'oxd-select-text')][1]")
    public WebElement nationalityDropdown;

    @FindBy(xpath = "//label[text()='Marital Status']/following::div[contains(@class,'oxd-select-text')][1]")
    public WebElement maritalStatusDropdown;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    public WebElement saveButton;

    public PersonalDetailsPage() {
        PageFactory.initElements(driver, this);
    }

}
