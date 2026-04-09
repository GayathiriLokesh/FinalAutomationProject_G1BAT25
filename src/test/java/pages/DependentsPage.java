package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public class DependentsPage extends CommonMethods {

    public DependentsPage() {
        PageFactory.initElements(driver, this);
    }

    // =========================
    // NAVIGATION / SECTION
    // =========================

    @FindBy(xpath = "//a[normalize-space()='Dependents']")
    public WebElement dependentsTab;

    @FindBy(xpath ="//h6[normalize-space()='Assigned Dependents']")
    public WebElement assignedDependentsHeader;

    @FindBy(xpath = "//h6[normalize-space()='Add Dependent']")
    public WebElement addDependentHeader;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Add'])[1]")
    public WebElement addButton;

    @FindBy(xpath = "//*[normalize-space()='Edit Dependent']")
    public WebElement editDependentHeader;

    // =========================
    // FORM FIELDS
    // =========================

    @FindBy(xpath = "//label[normalize-space()='Name']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement dependentNameField;

    @FindBy(xpath = "//label[normalize-space()='Relationship']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    public WebElement relationshipDropdown;

    @FindBy(xpath = "//label[normalize-space()='Please Specify']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement pleaseSpecifyField;

    @FindBy(xpath = "//label[normalize-space()='Date of Birth']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement dateOfBirthField;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
    public WebElement cancelButton;

    // =========================
    // VALIDATION / ERROR MESSAGES
    // =========================

    @FindBy(xpath = "//label[normalize-space()='Name']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']")
    public WebElement nameRequiredMessage;

    @FindBy(xpath = "//label[normalize-space()='Relationship']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']")
    public WebElement relationshipRequiredMessage;

    @FindBy(xpath = "//label[normalize-space()='Please Specify']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']")
    public WebElement pleaseSpecifyRequiredMessage;

    @FindBy(xpath = "//label[normalize-space()='Date of Birth']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']")
    public WebElement dobRequiredMessage;

    @FindBy(xpath = "//span[text()='Required']")
    public List<WebElement> allRequiredMessages;

    // =========================
    // DEPENDENTS LIST / TABLE
    // =========================

    @FindBy(xpath = "//div[@class='oxd-table-header']//div[contains(normalize-space(),'Name')]")
    public WebElement nameColumnHeader;

    @FindBy(xpath = "//div[@class='oxd-table-header']//div[contains(normalize-space(),'Relationship')]")
    public WebElement relationshipColumnHeader;

    @FindBy(xpath = "//div[@class='oxd-table-header']//div[contains(normalize-space(),'Date of Birth')]")
    public WebElement dobColumnHeader;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-card')]")
    public List<WebElement> dependentRows;

    @FindBy(xpath = "//div[contains(text(),'Record Found') or contains(text(),'Records Found')]")
    public WebElement recordFoundText;

    @FindBy(xpath = "//span[normalize-space()='No Records Found']")
    public WebElement noRecordsMessage;

    // =========================
    // DELETE FLOW
    // =========================

    @FindBy(xpath = "//button[contains(normalize-space(),'Delete Selected')]")
    public WebElement deleteSelectedButton;

    @FindBy(xpath = "//p[contains(normalize-space(),'The selected record will be permanently deleted')]")
    public WebElement deleteConfirmationMessage;

    @FindBy(xpath = "//button[normalize-space()='Yes, Delete']")
    public WebElement confirmDeleteButton;

    @FindBy(xpath = "//button[normalize-space()='No, Cancel']")
    public WebElement cancelDeleteButton;

    // =========================
    // DYNAMIC ELEMENTS
    // =========================

    public WebElement getDependentRow(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]"
        ));
    }

    public List<WebElement> getDependentRowsByName(String dependentName) {
        return driver.findElements(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]"
        ));
    }

    public WebElement getDependentNameCell(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//*[normalize-space()='" + dependentName + "']"
        ));
    }
    public WebElement getRelationshipCell(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//div[@role='cell'][3]"
        ));
    }

    public WebElement getDobCell(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//div[@role='cell'][4]"
        ));
    }



    public WebElement getEditButton(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//button[.//i[contains(@class,'bi-pencil')]]"
        ));
    }

    public WebElement getDeleteIconButton(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//button[.//i[contains(@class,'bi-trash')]]"
        ));
    }

    public WebElement getRowCheckbox(String dependentName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + dependentName + "']]//input[@type='checkbox']/ancestor::label"
        ));
    }

    public List<WebElement> getRelationshipOptions() {
        return driver.findElements(By.xpath(
                "//div[@role='option'] | //div[contains(@class,'oxd-select-option')]"
        ));
    }
}