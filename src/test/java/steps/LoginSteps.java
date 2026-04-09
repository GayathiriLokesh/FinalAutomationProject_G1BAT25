
package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;

public class LoginSteps extends CommonMethods {

    Map<String, String> loginData;

    @Given("admin user is on HRMS login page")
    public void admin_user_is_on_hrms_login_page() {
        Assert.assertTrue(isElementDisplayed(loginPage.usernameField));
        Assert.assertTrue(isElementDisplayed(loginPage.passwordField));
        Assert.assertTrue(isElementDisplayed(loginPage.loginButton));
    }

    @When("admin user enters valid username and password from excel row {int}")
    public void admin_user_enters_valid_username_and_password_from_excel_row(Integer rowNum) {
        loginData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.LOGIN_SHEET,
                rowNum
        );

        sendText(loginData.get("username"), loginPage.usernameField);
        sendText(loginData.get("password"), loginPage.passwordField);
    }

    @And("admin user clicks login button")
    public void admin_user_clicks_login_button() {
        click(loginPage.loginButton);
    }

    @Then("admin user should be redirected to admin dashboard")
    public void admin_user_should_be_redirected_to_admin_dashboard() {
        Assert.assertTrue(isElementDisplayed(dashboardPage.dashboardHeader));
        Assert.assertEquals("Dashboard", getText(dashboardPage.dashboardHeader));
        Assert.assertTrue(isElementDisplayed(dashboardPage.adminMenu));
        Assert.assertTrue(isElementDisplayed(dashboardPage.pimMenu));
    }
}