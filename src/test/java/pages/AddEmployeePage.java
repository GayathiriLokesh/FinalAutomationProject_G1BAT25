package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class AddEmployeePage extends CommonMethods {

    @FindBy(xpath = "//a[normalize-space()='Add Employee']")
    public WebElement addEmployeeTab;

    @FindBy(xpath = "//h6[normalize-space()='Add Employee']")
    public WebElement addEmployeeHeader;

    @FindBy(name = "firstName")
    public WebElement firstNameField;

    @FindBy(name = "middleName")
    public WebElement middleNameField;

    @FindBy(name = "lastName")
    public WebElement lastNameField;

    // Use this one field for both auto-generated ID and manually entered ID
    @FindBy(xpath = "//label[normalize-space()='Employee Id']/following::input[1]")
    public WebElement employeeIdField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;

    @FindBy(xpath = "//h6[text()='Personal Details']")
    public WebElement personalDetailsHeader;

    @FindBy(xpath = "//input[@name='firstName']/following::span[normalize-space()='Required'][1]")
    public WebElement firstNameRequiredMessage;

    @FindBy(xpath = "//input[@name='lastName']/following::span[normalize-space()='Required'][1]")
    public WebElement lastNameRequiredMessage;


    public AddEmployeePage() {
        PageFactory.initElements(driver, this);
    }
}