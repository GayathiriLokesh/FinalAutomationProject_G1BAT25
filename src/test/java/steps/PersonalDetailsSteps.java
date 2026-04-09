package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtils;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class PersonalDetailsSteps extends CommonMethods {

    private Map<String, String> personalData;

    @When("user enters ESS credentials from create login excel row {int}")
    public void user_enters_ess_credentials_from_create_login_excel_row(Integer rowNum) {
        Map<String, String> loginData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CREATE_LOGIN_SHEET,
                rowNum
        );

        sendText(loginData.get("Username"), loginPage.usernameField);
        sendText(loginData.get("Password"), loginPage.passwordField);
    }

    @And("ESS user navigates to My Info page")
    public void ess_user_navigates_to_my_info_page() {
        click(dashboardPage.myInfoMenu);
    }

    @Then("personal details page is displayed")
    public void personal_details_page_is_displayed() {
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.personalDetailsHeader));
        Assert.assertEquals("Personal Details", getText(personalDetailsPage.personalDetailsHeader).trim());
    }

    @And("personal details fields are displayed")
    public void personal_details_fields_are_displayed() {
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.firstNameField));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.middleNameField));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.lastNameField));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.maleRadioButton));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.femaleRadioButton));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.nationalityDropdown));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.maritalStatusDropdown));
        Assert.assertTrue(isElementDisplayed(personalDetailsPage.saveButton));
    }

    @When("ESS user updates personal details from excel row {int}")
    public void ess_user_updates_personal_details_from_excel_row(Integer rowNum) {
        personalData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.PERSONAL_DETAILS_SHEET,
                rowNum
        );

        waitForLoaderToDisappear();

        clearAndType(personalDetailsPage.firstNameField, personalData.get("firstName"));
        clearAndType(personalDetailsPage.middleNameField, personalData.get("middleName"));
        clearAndType(personalDetailsPage.lastNameField, personalData.get("lastName"));

        waitForLoaderToDisappear();

        String gender = personalData.get("gender");
        if ("Male".equalsIgnoreCase(gender)) {
            click(personalDetailsPage.maleRadioButton);
        } else if ("Female".equalsIgnoreCase(gender)) {
            click(personalDetailsPage.femaleRadioButton);
        }

        waitForLoaderToDisappear();
        click(personalDetailsPage.nationalityDropdown);
        selectDropdownOption(personalData.get("nationality"));

        waitForLoaderToDisappear();
        click(personalDetailsPage.maritalStatusDropdown);
        selectDropdownOption(personalData.get("maritalStatus"));
    }

    @And("ESS user saves personal details")
    public void ess_user_saves_personal_details() {
        click(personalDetailsPage.saveButton);
    }

    @Then("personal details are updated successfully")
    public void personal_details_are_updated_successfully() {
        waitForLoaderToDisappear();

        getWait().until(driver ->
                !personalDetailsPage.firstNameField.getAttribute("value").trim().isEmpty()
        );

        Assert.assertEquals(
                personalData.get("firstName"),
                personalDetailsPage.firstNameField.getAttribute("value").trim()
        );

        Assert.assertEquals(
                personalData.get("middleName"),
                personalDetailsPage.middleNameField.getAttribute("value").trim()
        );

        Assert.assertEquals(
                personalData.get("lastName"),
                personalDetailsPage.lastNameField.getAttribute("value").trim()
        );

        Assert.assertEquals(
                personalData.get("nationality"),
                getText(personalDetailsPage.nationalityDropdown).trim()
        );

        Assert.assertEquals(
                personalData.get("maritalStatus"),
                getText(personalDetailsPage.maritalStatusDropdown).trim()
        );

        // Do NOT validate gender radio button state in UI after save.
        // This app does not reliably persist the selected radio state after page refresh.
        // Gender is validated in database step instead.
    }

    private String getGenderCode(String gender) {
        if ("Male".equalsIgnoreCase(gender)) {
            return "1";
        } else if ("Female".equalsIgnoreCase(gender)) {
            return "2";
        }
        throw new RuntimeException("Unsupported gender value: " + gender);
    }

    @And("personal details are updated in database")
    public void personal_details_are_updated_in_database() {
        DBUtils.connectToDB();

        String employeeId = personalDetailsPage.employeeIdField.getAttribute("value").trim();
        System.out.println("employeeId = " + employeeId);

        String query =
                "select emp_firstname, emp_middle_name, emp_lastname, emp_gender, emp_marital_status " +
                        "from hs_hr_employee " +
                        "where employee_id = '" + employeeId + "'";

        List<Map<String, String>> results = DBUtils.fetch(query);

        DBUtils.closeDBConnection();

        Assert.assertFalse("No personal details record found in database", results.isEmpty());

        Map<String, String> dbRow = results.get(0);
        System.out.println("DB Row = " + dbRow);

        Assert.assertEquals(personalData.get("firstName"), dbRow.get("emp_firstname"));
        Assert.assertEquals(personalData.get("middleName"), dbRow.get("emp_middle_name"));
        Assert.assertEquals(personalData.get("lastName"), dbRow.get("emp_lastname"));
        Assert.assertEquals(getGenderCode(personalData.get("gender")), dbRow.get("emp_gender"));
        Assert.assertEquals(personalData.get("maritalStatus"), dbRow.get("emp_marital_status"));
    }
}