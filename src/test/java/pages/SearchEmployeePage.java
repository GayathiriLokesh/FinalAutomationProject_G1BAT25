package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public class SearchEmployeePage extends CommonMethods {
    @FindBy(xpath = "//a[normalize-space()='Employee List']")
    public WebElement employeeListTab;

    @FindBy(xpath = "(//input[@placeholder='Type for hints...'])[1]")
    public WebElement employeeNameField;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    public WebElement employeeIdField;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@role='row']")
    public List<WebElement> resultRows;

    @FindBy(xpath = "//span[normalize-space()='No Records Found']")
    public WebElement noRecordsFoundMessage;


    public SearchEmployeePage() {
        PageFactory.initElements(driver, this);
    }
}
