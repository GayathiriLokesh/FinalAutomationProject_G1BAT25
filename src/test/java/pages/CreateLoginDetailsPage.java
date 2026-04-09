package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public class CreateLoginDetailsPage extends CommonMethods {


    @FindBy(xpath = "//button[.//i[contains(@class,'plus')]]")
    public WebElement addButton;

    @FindBy(xpath = "//h6[text()='Add User']")
    public WebElement addUserHeader;


    @FindBy(xpath = "(//div[contains(@class,'oxd-select-text')])[1]")
    public WebElement userRoleDropdown;

    @FindBy(xpath = "//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]")
    public WebElement statusDropdown;


    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    public WebElement employeeNameField;

    @FindBy(xpath = "//div[@role='listbox']")
    public WebElement employeeSuggestionBox;


    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    public WebElement usernameField;


    @FindBy(xpath = "//label[text()='Password']/following::input[1]")
    public WebElement passwordField;

    @FindBy(xpath = "//label[text()='Confirm Password']/following::input[1]")
    public WebElement confirmPasswordField;


    @FindBy(xpath = "//button[normalize-space()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    public WebElement cancelButton;


    @FindBy(xpath = "//span[text()='Required']")
    public List<WebElement> requiredErrors;

    @FindBy(xpath = "//*[contains(text(),'match') or contains(text(),'Match')]")
    public WebElement passwordMismatchMessage;

    @FindBy(xpath = "//*[contains(text(),'Should have at least') or contains(text(),'character')]")
    public List<WebElement> passwordRuleMessages;

    @FindBy(xpath = "//*[contains(text(),'at least 8 characters')]")
    public WebElement minLengthMessage;

    @FindBy(xpath = "//*[contains(text(),'upper-case')]")
    public WebElement uppercaseMessage;

    @FindBy(xpath = "//*[contains(text(),'special character')]")
    public WebElement specialCharMessage;

    @FindBy(xpath = "//*[contains(text(),'Weak') or contains(text(),'Better') or contains(text(),'Strong')]")
    public WebElement passwordStrengthIndicator;


    @FindBy(xpath = "//div[@role='option']")
    public List<WebElement> dropdownOptions;

    public CreateLoginDetailsPage() {
        PageFactory.initElements(driver, this);
    }
}