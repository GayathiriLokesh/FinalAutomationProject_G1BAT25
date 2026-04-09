package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtils;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class DependentsSteps extends CommonMethods {

    private Map<String, String> dependentData;
    private String currentDependentName;

    @When("user navigates to My Info dependents page")
    public void user_navigates_to_my_info_dependents_page() {
        click(dashboardPage.myInfoMenu);
        //getWait().until(ExpectedConditions.elementToBeClickable(dependentsPage.dependentsTab));
        click(dependentsPage.dependentsTab);
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.assignedDependentsHeader));
    }

    @Then("dependents section is displayed")
    public void dependents_section_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.addButton));

        Assert.assertTrue(isElementDisplayed(dependentsPage.addButton));
        //Assert.assertTrue(driver.getPageSource().contains("Dependents"));

    }

    @And("dependent form fields are displayed")
    public void dependent_form_fields_are_displayed() {
        click(dependentsPage.addButton);
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.addDependentHeader));

        Assert.assertTrue(isElementDisplayed(dependentsPage.dependentNameField));
        Assert.assertTrue(isElementDisplayed(dependentsPage.relationshipDropdown));
        Assert.assertTrue(isElementDisplayed(dependentsPage.dateOfBirthField));
        Assert.assertTrue(isElementDisplayed(dependentsPage.saveButton));
        Assert.assertTrue(isElementDisplayed(dependentsPage.cancelButton));
    }

    @And("dependents list columns are displayed")
    public void dependents_list_columns_are_displayed() {
        Assert.assertTrue(isElementDisplayed(dependentsPage.nameColumnHeader));
        Assert.assertTrue(isElementDisplayed(dependentsPage.relationshipColumnHeader));
        Assert.assertTrue(isElementDisplayed(dependentsPage.dobColumnHeader));
    }

    @And("user clicks add dependent button")
    public void user_clicks_add_dependent_button() {
        click(dependentsPage.addButton);
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.addDependentHeader));
    }

    @And("user enters dependent details from excel row {int}")
    public void user_enters_dependent_details_from_excel_row(Integer rowNum) {
        dependentData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.DEPENDENTS_SHEET,
                rowNum
        );

        currentDependentName = getValue("Name");

        if (!currentDependentName.isEmpty()) {
            clearAndType(dependentsPage.dependentNameField, currentDependentName);
        }

        String relationship = getValue("Relationship");
        if (!relationship.isEmpty()) {
            click(dependentsPage.relationshipDropdown);
            selectRelationshipIgnoringCase(relationship);

            if (relationship.equalsIgnoreCase("Other")) {
                getWait().until(ExpectedConditions.visibilityOf(dependentsPage.pleaseSpecifyField));
                String pleaseSpecify = getValue("PleaseSpecify");
                if (!pleaseSpecify.isEmpty()) {
                    clearAndType(dependentsPage.pleaseSpecifyField, pleaseSpecify);
                }
            }
        }

        String dob = getValue("DateOfBirth");
        if (!dob.isEmpty()) {
            clearAndType(dependentsPage.dateOfBirthField, dob);
            dependentsPage.dateOfBirthField.sendKeys(Keys.TAB);
            getWait().until(ExpectedConditions.attributeToBeNotEmpty(
                    dependentsPage.dateOfBirthField, "value"
            ));
            waitForLoaderToDisappear();
        }
    }

    @And("user clicks save button on dependents page")
    public void user_clicks_save_button_on_dependents_page() {
        click(dependentsPage.saveButton);
    }

    @Then("dependent is added successfully")
    public void dependent_is_added_successfully() {
        By dependentRowLocator = By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + currentDependentName + "']]"
        );

        getWait().until(ExpectedConditions.visibilityOfElementLocated(dependentRowLocator));
        Assert.assertTrue(isElementDisplayed(driver.findElement(dependentRowLocator)));
    }

    @And("added dependent details are displayed in the dependents list")
    public void added_dependent_details_are_displayed_in_the_dependents_list() {
        Assert.assertTrue(isElementDisplayed(dependentsPage.getDependentNameCell(currentDependentName)));

        String expectedRelationship;
        if (getValue("Relationship").equalsIgnoreCase("Other")) {
            expectedRelationship = getValue("PleaseSpecify");
        } else {
            expectedRelationship = getValue("Relationship");
        }

        Assert.assertEquals(
                expectedRelationship,
                dependentsPage.getRelationshipCell(currentDependentName).getText().trim()
        );

        Assert.assertEquals(
                getValue("DateOfBirth"),
                dependentsPage.getDobCell(currentDependentName).getText().trim()
        );
    }

    @Then("dependent required field errors are displayed")
    public void dependent_required_field_errors_are_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.nameRequiredMessage));
        Assert.assertTrue(isElementDisplayed(dependentsPage.nameRequiredMessage));
        Assert.assertTrue(isElementDisplayed(dependentsPage.relationshipRequiredMessage));
        //Assert.assertTrue(isElementDisplayed(dependentsPage.dobRequiredMessage));
    }

    @Then("please specify field is displayed")
    public void please_specify_field_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.pleaseSpecifyField));
        Assert.assertTrue(isElementDisplayed(dependentsPage.pleaseSpecifyField));
    }

    @Then("please specify required field error is displayed")
    public void please_specify_required_field_error_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.pleaseSpecifyRequiredMessage));
        Assert.assertTrue(isElementDisplayed(dependentsPage.pleaseSpecifyRequiredMessage));
    }

    @And("dependent record is saved in database")
    public void dependent_record_is_saved_in_database() {
        DBUtils.connectToDB();

        String query =
                "select ed_name, ed_relationship_type, ed_relationship, ed_date_of_birth " +
                        "from hs_hr_emp_dependents " +
                        "where ed_name = '" + currentDependentName + "' " +
                        "order by ed_seqno desc limit 1";

        List<Map<String, String>> results = DBUtils.fetch(query);
        DBUtils.closeDBConnection();

        Assert.assertFalse("No dependent record found in database", results.isEmpty());

        Map<String, String> dbRow = results.get(0);

        Assert.assertEquals(currentDependentName, safe(dbRow.get("ed_name")));
        Assert.assertEquals(
                getValue("Relationship").toLowerCase(),
                safe(dbRow.get("ed_relationship_type")).toLowerCase()
        );
        Assert.assertEquals(
                getValue("DateOfBirth"),
                safe(dbRow.get("ed_date_of_birth"))
        );

        if (getValue("Relationship").equalsIgnoreCase("Other")) {
            Assert.assertEquals(
                    getValue("PleaseSpecify").toLowerCase(),
                    safe(dbRow.get("ed_relationship")).toLowerCase()
            );
        }
    }

    @And("user edits dependent using excel row {int}")
    public void user_edits_dependent_using_excel_row(Integer rowNum) {
        dependentData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.DEPENDENTS_SHEET,
                rowNum
        );

        String oldDependentName = getValue("OldName");
        currentDependentName = getValue("Name");

        WebElement editButton = dependentsPage.getEditButton(oldDependentName);
        getWait().until(ExpectedConditions.elementToBeClickable(editButton));
        click(editButton);

        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.editDependentHeader));

        clearAndType(dependentsPage.dependentNameField, currentDependentName);

        String relationship = getValue("Relationship");
        if (!relationship.isEmpty()) {
            click(dependentsPage.relationshipDropdown);
            selectRelationshipIgnoringCase(relationship);

            if (relationship.equalsIgnoreCase("Other")) {
                getWait().until(ExpectedConditions.visibilityOf(dependentsPage.pleaseSpecifyField));
                clearAndType(dependentsPage.pleaseSpecifyField, getValue("PleaseSpecify"));
            }
        }

        String dob = getValue("DateOfBirth");
        if (!dob.isEmpty()) {
            clearAndType(dependentsPage.dateOfBirthField, dob);
            dependentsPage.dateOfBirthField.sendKeys(Keys.TAB);
            waitForLoaderToDisappear();
        }
    }

    @Then("dependent changes are updated successfully")
    public void dependent_changes_are_updated_successfully() {
        By updatedRowLocator = By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + currentDependentName + "']]"
        );

        getWait().until(ExpectedConditions.visibilityOfElementLocated(updatedRowLocator));
        Assert.assertTrue(isElementDisplayed(driver.findElement(updatedRowLocator)));
    }
    @And("updated dependent details are displayed in the dependents list")
    public void updated_dependent_details_are_displayed_in_the_dependents_list() {
        Assert.assertTrue(isElementDisplayed(dependentsPage.getDependentNameCell(currentDependentName)));

        String expectedRelationship;
        if (getValue("Relationship").equalsIgnoreCase("Other")) {
            expectedRelationship = getValue("PleaseSpecify");
        } else {
            expectedRelationship = getValue("Relationship");
        }

        Assert.assertEquals(
                expectedRelationship,
                dependentsPage.getRelationshipCell(currentDependentName).getText().trim()
        );

        Assert.assertEquals(
                getValue("DateOfBirth"),
                dependentsPage.getDobCell(currentDependentName).getText().trim()
        );
    }

    @And("updated dependent record is saved in database")
    public void updated_dependent_record_is_saved_in_database() {
        DBUtils.connectToDB();

        String query =
                "select ed_name, ed_relationship_type, ed_relationship, ed_date_of_birth " +
                        "from hs_hr_emp_dependents " +
                        "where ed_name = '" + currentDependentName + "' " +
                        "order by ed_seqno desc limit 1";

        List<Map<String, String>> results = DBUtils.fetch(query);
        DBUtils.closeDBConnection();

        Assert.assertFalse("Updated dependent record not found in database", results.isEmpty());

        Map<String, String> dbRow = results.get(0);

        Assert.assertEquals(currentDependentName, safe(dbRow.get("ed_name")));
        Assert.assertEquals(
                getValue("Relationship").toLowerCase(),
                safe(dbRow.get("ed_relationship_type")).toLowerCase()
        );
        Assert.assertEquals(
                getValue("DateOfBirth"),
                safe(dbRow.get("ed_date_of_birth"))
        );

        if (getValue("Relationship").equalsIgnoreCase("Other")) {
            Assert.assertEquals(
                    getValue("PleaseSpecify").toLowerCase(),
                    safe(dbRow.get("ed_relationship")).toLowerCase()
            );
        }
    }

    @And("user deletes dependent from excel row {int}")
    public void user_deletes_dependent_from_excel_row(Integer rowNum) {
        dependentData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.DEPENDENTS_SHEET,
                rowNum
        );

        currentDependentName = getValue("Name");

        WebElement checkbox = dependentsPage.getRowCheckbox(currentDependentName);
        getWait().until(ExpectedConditions.elementToBeClickable(checkbox));
        click(checkbox);

        getWait().until(ExpectedConditions.elementToBeClickable(dependentsPage.deleteSelectedButton));
        click(dependentsPage.deleteSelectedButton);

        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.deleteConfirmationMessage));
        Assert.assertTrue(isElementDisplayed(dependentsPage.confirmDeleteButton));
        click(dependentsPage.confirmDeleteButton);
    }

    @Then("dependent is removed successfully")
    public void dependent_is_removed_successfully() {
        By deletedRowLocator = By.xpath(
                "//div[contains(@class,'oxd-table-card')][.//*[normalize-space()='" + currentDependentName + "']]"
        );

        getWait().until(ExpectedConditions.invisibilityOfElementLocated(deletedRowLocator));

        Assert.assertTrue(
                "Dependent still exists after deletion",
                dependentsPage.getDependentRowsByName(currentDependentName).isEmpty()
        );
    }

    @And("delete confirmation popup is displayed")
    public void delete_confirmation_popup_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(dependentsPage.deleteConfirmationMessage));
        Assert.assertTrue(isElementDisplayed(dependentsPage.confirmDeleteButton));
        Assert.assertTrue(isElementDisplayed(dependentsPage.cancelDeleteButton));
    }

    private void selectRelationshipIgnoringCase(String relationshipValue) {
        List<WebElement> options = getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@role='option'] | //div[contains(@class,'oxd-select-option')]")
                )
        );
        

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(relationshipValue)) {
                option.click();
                return;
            }
        }

        throw new RuntimeException("Dropdown option not found: " + relationshipValue);
    }



    private String getValue(String key) {

        return dependentData.get(key) == null ? "" : dependentData.get(key).trim();
    }

    private String safe(String value) {

        return value == null ? "" : value.trim();
    }
}