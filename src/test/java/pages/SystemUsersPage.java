package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class SystemUsersPage extends CommonMethods {

    @FindBy(xpath = "//h6[normalize-space()='System Users']")
    public WebElement systemUsersHeader;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    public WebElement usernameSearchField;

    @FindBy(xpath = "(//div[contains(text(),'-- Select --')])[1]")
    public WebElement userRoleDropdown;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    public WebElement employeeNameSearchField;

    @FindBy(xpath = "(//div[contains(text(),'-- Select --')])[2]")
    public WebElement statusDropdown;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    public WebElement resetButton;

    @FindBy(xpath = "//div[@role='table']")
    public WebElement resultTable;

    public SystemUsersPage() {
        PageFactory.initElements(driver, this);
    }
}