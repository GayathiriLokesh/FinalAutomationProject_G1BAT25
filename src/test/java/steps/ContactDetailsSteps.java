package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;

public class ContactDetailsSteps extends CommonMethods {
    @Given("ESS user logs in credentials from create login excel row {int}")
    public void ess_user_logs_in_credentials_from_create_login_excel_row(Integer rowNum) {
        Map<String, String> loginData = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CREATE_LOGIN_SHEET,
                rowNum
        );

        sendText(loginData.get("Username"), loginPage.usernameField);
        sendText(loginData.get("Password"), loginPage.passwordField);
        click(loginPage.loginButton);
    }

    Map<String, String> data;

    @When("user navigates to My Info contact details page")
    public void user_navigates_to_my_info_contact_details_page() {
        click(dashboardPage.myInfoMenu);
        click(contactDetailsPage.contactDetailsTab);
    }

    @Then("contact details form is displayed with all required fields")
    public void contact_details_form_is_displayed_with_all_required_fields() {

        // Address section
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.addressStreet1Field));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.addressStreet2Field));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.cityField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.stateField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.zipCodeField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.countryDropdown));

        // Telephone section
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.homePhoneField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.mobilePhoneField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.workPhoneField));

        // Email section
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.workEmailField));
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.otherEmailField));

        // Save button
        Assert.assertTrue(isElementDisplayed(contactDetailsPage.saveButton));
    }

    @And("user enters contact details from excel row {int}")
    public void user_enters_contact_details_from_excel_row(Integer rowNum) {

        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.CONTACT_DETAILS_SHEET,
                rowNum
        );

        clearAndType(contactDetailsPage.addressStreet1Field, data.get("Street1"));
        clearAndType(contactDetailsPage.addressStreet2Field, data.get("Street2"));
        clearAndType(contactDetailsPage.cityField, data.get("City"));
        clearAndType(contactDetailsPage.stateField, data.get("State"));
        clearAndType(contactDetailsPage.zipCodeField, data.get("Zipcode"));

        click(contactDetailsPage.countryDropdown);
        selectDynamicOption(data.get("Country").trim());

        clearAndType(contactDetailsPage.homePhoneField, data.get("HomePhone"));
        clearAndType(contactDetailsPage.mobilePhoneField, data.get("Mobile"));
        clearAndType(contactDetailsPage.workPhoneField, data.get("WorkPhone"));
        clearAndType(contactDetailsPage.workEmailField, data.get("WorkEmail"));
        clearAndType(contactDetailsPage.otherEmailField, data.get("OtherEmail"));
    }

    @And("user clicks save button")
    public void user_clicks_save_button() {
        click(contactDetailsPage.saveButton);
    }

    @Then("contact details are updated successfully")
    public void contact_details_are_updated_successfully() {

        Assert.assertEquals(data.get("Street1"),
                contactDetailsPage.addressStreet1Field.getAttribute("value"));

        Assert.assertEquals(data.get("City"),
                contactDetailsPage.cityField.getAttribute("value"));

        Assert.assertEquals(data.get("State"),
                contactDetailsPage.stateField.getAttribute("value"));

        Assert.assertEquals(data.get("Zipcode"),
                contactDetailsPage.zipCodeField.getAttribute("value"));

        Assert.assertEquals(data.get("Mobile"),
                contactDetailsPage.mobilePhoneField.getAttribute("value"));

        Assert.assertEquals(data.get("WorkEmail"),
                contactDetailsPage.workEmailField.getAttribute("value"));
    }
}