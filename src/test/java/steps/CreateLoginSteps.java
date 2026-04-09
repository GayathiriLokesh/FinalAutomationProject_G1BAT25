package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;
import java.util.Objects;

public class CreateLoginSteps extends CommonMethods {

    Map<String, String> data;
    String selectedEmployeeName;

    @Given("user is on HRMS login page")
    public void user_is_on_hrms_login_page() {
        Assert.assertTrue(isElementDisplayed(loginPage.usernameField));
        Assert.assertTrue(isElementDisplayed(loginPage.passwordField));
        Assert.assertTrue(isElementDisplayed(loginPage.loginButton));
    }

    @When("user enters admin credentials from login excel row {int}")
    public void user_enters_admin_credentials_from_login_excel_row(Integer rowNum) {
        Map<String, String> loginData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.LOGIN_SHEET,
                rowNum
        );

        sendText(loginData.get("username"), loginPage.usernameField);
        sendText(loginData.get("password"), loginPage.passwordField);
    }

    @When("user navigates to add user page")
    public void user_navigates_to_add_user_page() {
        click(dashboardPage.adminMenu);
        waitForVisibility(createLoginDetailsPage.addButton);
        click(createLoginDetailsPage.addButton);
        waitForVisibility(createLoginDetailsPage.addUserHeader);
    }

    @Then("add user page is displayed")
    public void add_user_page_is_displayed() {
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.addUserHeader));
        Assert.assertEquals("Add User", getText(createLoginDetailsPage.addUserHeader));
    }

    @And("all required create login fields are displayed")
    public void all_required_create_login_fields_are_displayed() {
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.userRoleDropdown));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.employeeNameField));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.statusDropdown));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.usernameField));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.passwordField));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.confirmPasswordField));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.saveButton));
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.cancelButton));
    }

    @And("user submits create login form from excel row {int}")
    public void user_submits_create_login_form_from_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CREATE_LOGIN_SHEET,
                rowNum
        );

        fillCreateLoginForm(data);
        click(createLoginDetailsPage.saveButton);
    }

    @Then("required field errors are displayed for create login form")
    public void required_field_errors_are_displayed_for_create_login_form() {
        Assert.assertFalse("Required field errors were not displayed",
                createLoginDetailsPage.requiredErrors.isEmpty());
    }

    @And("user types employee name from create login excel row {int}")
    public void user_types_employee_name_from_create_login_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CREATE_LOGIN_SHEET,
                rowNum
        );

        sendText(data.get("EmployeeName"), createLoginDetailsPage.employeeNameField);
    }

    @Then("employee suggestions are displayed")
    public void employee_suggestions_are_displayed() {
        waitForVisibility(createLoginDetailsPage.employeeSuggestionBox);
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.employeeSuggestionBox));
    }

    @And("user selects employee from create login excel row {int}")
    public void user_selects_employee_from_create_login_excel_row(Integer rowNum) {
        if (data == null) {
            data = ExcelReader.readRow(
                    Constants.EXCEL_FILE_PATH,
                    Constants.CREATE_LOGIN_SHEET,
                    rowNum
            );
        }

        selectedEmployeeName = data.get("EmployeeName");

        selectEmployeeFromVisibleSuggestions(selectedEmployeeName);
    }

    @And("user fills create login form from excel row {int}")
    public void user_fills_create_login_form_from_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CREATE_LOGIN_SHEET,
                rowNum
        );

        fillCreateLoginForm(data);
        selectedEmployeeName = data.get("EmployeeName");
    }

    @Then("password rule validation messages are displayed")
    public void password_rule_validation_messages_are_displayed() {
        getWait().until(driver ->
                createLoginDetailsPage.passwordField.getAttribute("class").contains("error")
                        || !createLoginDetailsPage.passwordRuleMessages.isEmpty()
        );

        Assert.assertTrue(
                createLoginDetailsPage.passwordField.getAttribute("class").contains("error")
                        || !createLoginDetailsPage.passwordRuleMessages.isEmpty()
        );
    }
    @And("password strength indicator should be strong")
    public void password_strength_indicator_should_be_strong() {
        waitForVisibility(createLoginDetailsPage.passwordStrengthIndicator);
        String strength = getText(createLoginDetailsPage.passwordStrengthIndicator).toLowerCase();

        Assert.assertTrue(
                "Password strength is not strong enough: " + strength,
                strength.contains("strong"));
    }

    @And("password strength indicator is displayed")
    public void password_strength_indicator_is_displayed() {
        Assert.assertTrue(isElementDisplayed(createLoginDetailsPage.passwordStrengthIndicator));
    }

    @Then("password mismatch error is displayed")
    public void password_mismatch_error_is_displayed() {
        createLoginDetailsPage.confirmPasswordField.sendKeys(Keys.TAB);

        getWait().until(driver ->
                driver.getPageSource().toLowerCase().contains("match")
        );

        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("match")
        );
    }

    @And("user saves create login form")
    public void user_saves_create_login_form() {
        click(createLoginDetailsPage.saveButton);
    }

    @Then("ESS user is created successfully")
    public void ess_user_is_created_successfully() {
        getWait().until(driver ->
                Objects.requireNonNull(driver.getPageSource()).contains("System Users")
                        || driver.getPageSource().contains("Successfully Saved")
                        || driver.getPageSource().contains("Success")
        );

        Assert.assertTrue(
                Objects.requireNonNull(driver.getPageSource()).contains("System Users")
                        || driver.getPageSource().contains("Successfully Saved")
                        || driver.getPageSource().contains("Success")
        );
    }

    @And("created ESS user is linked to selected employee from excel row {int}")
    public void created_ess_user_is_linked_to_selected_employee_from_excel_row(Integer rowNum) {
        if (data == null) {
            data = ExcelReader.readRow(
                    Constants.EXCEL_FILE_PATH,
                    Constants.CREATE_LOGIN_SHEET,
                    rowNum
            );
        }

        sendText(data.get("Username"), systemUsersPage.usernameSearchField);
        click(systemUsersPage.searchButton);

        waitForVisibility(systemUsersPage.resultTable);
        Assert.assertTrue(isElementDisplayed(systemUsersPage.resultTable));
    }




    private void fillCreateLoginForm(Map<String, String> rowData) {

        if (rowData.get("UserRole") != null && !rowData.get("UserRole").trim().isEmpty()) {
            click(createLoginDetailsPage.userRoleDropdown);
            selectDynamicOption(rowData.get("UserRole"));
        }

        if (rowData.get("EmployeeName") != null && !rowData.get("EmployeeName").trim().isEmpty()) {
            selectEmployeeFromAutoComplete(
                    createLoginDetailsPage.employeeNameField,
                    rowData.get("EmployeeName")
            );
        }

        if (rowData.get("Status") != null && !rowData.get("Status").trim().isEmpty()) {
            waitForElementToBeClickable(createLoginDetailsPage.statusDropdown);
            click(createLoginDetailsPage.statusDropdown);
            selectDynamicOption(rowData.get("Status"));
        }

        if (rowData.get("Username") != null && !rowData.get("Username").trim().isEmpty()) {
            sendText(rowData.get("Username"), createLoginDetailsPage.usernameField);
        }

        if (rowData.get("Password") != null && !rowData.get("Password").trim().isEmpty()) {
            typeSlowly(createLoginDetailsPage.passwordField, rowData.get("Password").trim());}

        if (rowData.get("ConfirmPassword") != null && !rowData.get("ConfirmPassword").trim().isEmpty()) {
            typeSlowly(createLoginDetailsPage.confirmPasswordField, rowData.get("ConfirmPassword").trim());
        }
        }
    }
