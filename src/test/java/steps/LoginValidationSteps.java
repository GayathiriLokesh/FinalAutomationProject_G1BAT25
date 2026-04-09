package steps;



import io.cucumber.java.en.*;
import org.junit.Assert;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;

public class LoginValidationSteps extends CommonMethods {

    Map<String, String> loginData;

    @When("user enters login credentials from login excel row {int}")
    public void user_enters_login_credentials_from_login_excel_row(Integer rowNum) {
        loginData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.LOGIN_SHEET,
                rowNum
        );

        sendText(loginData.get("username"), loginPage.usernameField);
        sendText(loginData.get("password"), loginPage.passwordField);
    }

    @And("user clicks on login button")
    public void user_clicks_on_login_button() {
        click(loginPage.loginButton);
    }

    @Then("user is logged in successfully")
    public void user_is_logged_in_successfully() {
        Assert.assertTrue(isElementDisplayed(dashboardPage.dashboardHeader));
        Assert.assertEquals("Dashboard", getText(dashboardPage.dashboardHeader));
    }

    @Then("invalid credentials error is displayed")
    public void invalid_credentials_error_is_displayed() {
        Assert.assertTrue(isElementDisplayed(loginPage.invalidCredentialsMessage));
        Assert.assertEquals("Invalid credentials", getText(loginPage.invalidCredentialsMessage));
    }

    @Then("username required error is displayed")
    public void username_required_error_is_displayed() {
        Assert.assertTrue(isElementDisplayed(loginPage.usernameRequiredMessage));
        Assert.assertEquals("Required", getText(loginPage.usernameRequiredMessage));
    }

    @Then("password required error is displayed")
    public void password_required_error_is_displayed() {
        Assert.assertTrue(isElementDisplayed(loginPage.passwordRequiredMessage));
        Assert.assertEquals("Required", getText(loginPage.passwordRequiredMessage));
    }

    @And("user remains on login page")
    public void user_remains_on_login_page() {
        Assert.assertTrue(isElementDisplayed(loginPage.usernameField));
        Assert.assertTrue(isElementDisplayed(loginPage.passwordField));
        Assert.assertTrue(isElementDisplayed(loginPage.loginButton));
    }
}