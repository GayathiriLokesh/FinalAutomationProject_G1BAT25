package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtils;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {

    Map<String, String> addEmployeeData;
    String actualSavedEmployeeId;

    @When("user navigates to add employee page")
    public void user_navigates_to_add_employee_page() {
        click(dashboardPage.pimMenu);
        click(addEmployeePage.addEmployeeTab);
        waitForVisibility(addEmployeePage.addEmployeeHeader);
        Assert.assertTrue(isElementDisplayed(addEmployeePage.addEmployeeHeader));
    }

    @And("user enters add employee details from excel row {int}")
    public void user_enters_add_employee_details_from_excel_row(Integer rowNum) {
        addEmployeeData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.ADD_EMPLOYEE_SHEET,
                rowNum
        );

        sendText(addEmployeeData.get("FirstName"), addEmployeePage.firstNameField);
        sendText(addEmployeeData.get("MiddleName"), addEmployeePage.middleNameField);
        sendText(addEmployeeData.get("LastName"), addEmployeePage.lastNameField);

        String empId = addEmployeeData.get("EmployeeId");

        if (empId != null && !empId.trim().isEmpty()) {
            addEmployeePage.employeeIdField.click();
            addEmployeePage.employeeIdField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            addEmployeePage.employeeIdField.sendKeys(Keys.DELETE);

            addEmployeePage.employeeIdField.sendKeys(empId.trim());
        }
    }

    @And("system generates employee ID automatically")
    public void system_generates_employee_id_automatically() {
        actualSavedEmployeeId = addEmployeePage.employeeIdField.getAttribute("value").trim();

        Assert.assertNotNull(actualSavedEmployeeId);
        Assert.assertFalse("Employee ID should be generated", actualSavedEmployeeId.isEmpty());

        System.out.println("Generated Employee ID before save: " + actualSavedEmployeeId);
    }

    @And("user clicks save button on add employee page")
    public void user_clicks_save_button_on_add_employee_page() {
        click(addEmployeePage.saveButton);
    }

    @And("entered employee ID replaces auto generated employee ID")
    public void entered_employee_id_replaces_auto_generated_employee_id() {
        String enteredEmployeeId = addEmployeeData.get("EmployeeId").trim();
        String currentEmployeeId = addEmployeePage.employeeIdField.getAttribute("value").trim();

        Assert.assertEquals("Employee ID was not replaced correctly",
                enteredEmployeeId, currentEmployeeId);

        actualSavedEmployeeId = currentEmployeeId;
    }

    @Then("employee is added successfully")
    public void employee_is_added_successfully() {
        waitForVisibility(addEmployeePage.personalDetailsHeader);

        Assert.assertTrue(isElementDisplayed(addEmployeePage.personalDetailsHeader));
        Assert.assertEquals("Personal Details",
                getText(addEmployeePage.personalDetailsHeader).trim());
    }

    @And("saved employee ID contains entered employee ID")
    public void saved_employee_id_contains_entered_employee_id() {
        actualSavedEmployeeId = addEmployeePage.employeeIdField.getAttribute("value").trim();
        String enteredEmployeeId = addEmployeeData.get("EmployeeId").trim();

        Assert.assertFalse("Saved employee ID is empty", actualSavedEmployeeId.isEmpty());
        Assert.assertTrue("Saved employee ID does not contain entered employee ID",
                actualSavedEmployeeId.contains(enteredEmployeeId));

        System.out.println("Entered Employee ID: " + enteredEmployeeId);
        System.out.println("Employee ID in field before save: " + actualSavedEmployeeId);
    }

    @Then("first name required error is displayed")
    public void first_name_required_error_is_displayed() {
        Assert.assertTrue(isElementDisplayed(addEmployeePage.firstNameRequiredMessage));
        Assert.assertEquals("Required",
                getText(addEmployeePage.firstNameRequiredMessage).trim());
    }

    @Then("last name required error is displayed")
    public void last_name_required_error_is_displayed() {
        Assert.assertTrue(isElementDisplayed(addEmployeePage.lastNameRequiredMessage));
        Assert.assertEquals("Required",
                getText(addEmployeePage.lastNameRequiredMessage).trim());
    }

    @And("added employee record is saved in database")
    public void added_employee_record_is_saved_in_database() {
        System.out.println("Employee ID used for DB validation: " + actualSavedEmployeeId);

        DBUtils.connectToDB();

        String query = "SELECT * FROM hs_hr_employee WHERE employee_id = '" + actualSavedEmployeeId + "'";
        System.out.println("Executing query: " + query);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Map<String, String>> result = DBUtils.fetch(query);

        DBUtils.closeDBConnection();

        Assert.assertFalse("Employee not found in DB", result.isEmpty());

        Map<String, String> row = result.get(0);

        Assert.assertEquals(addEmployeeData.get("FirstName"), row.get("emp_firstname"));
        Assert.assertEquals(addEmployeeData.get("LastName"), row.get("emp_lastname"));
    }
}