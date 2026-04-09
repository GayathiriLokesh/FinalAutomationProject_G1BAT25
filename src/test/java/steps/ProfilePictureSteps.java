package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;

public class ProfilePictureSteps extends CommonMethods {

    @When("ESS user uploads profile picture from excel row {int}")
    public void ess_user_uploads_profile_picture_from_excel_row(Integer rowNum) {
        Map<String, String> data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.PROFILE_PICTURE_SHEET,
                rowNum
        );

        String fullPath = System.getProperty("user.dir") + "/" + data.get("filepath");

        // click avatar/image area first so upload control appears
        click(profilePicturePage.profileAvatar);

        WebElement fileInput = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@type='file']")
                )
        );

        fileInput.sendKeys(fullPath);
    }

    @And("ESS user clicks upload button")
    public void ess_user_clicks_upload_button() {
        click(profilePicturePage.uploadButton);
    }

    @Then("profile picture is uploaded successfully")
    public void profile_picture_is_uploaded_successfully() {
        Assert.assertTrue(isElementDisplayed(profilePicturePage.uploadedProfileImage));
    }

    @Then("unsupported profile picture format error is displayed")
    public void unsupported_profile_picture_format_error_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(profilePicturePage.fileFormatErrorMessage));

        Assert.assertTrue("Unsupported format error is not displayed",
                profilePicturePage.fileFormatErrorMessage.isDisplayed());

        Assert.assertEquals("Incorrect validation message",
                "File type not allowed",
                profilePicturePage.fileFormatErrorMessage.getText().trim());
    }

    @Then("profile picture size error is displayed")
    public void profile_picture_size_error_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(profilePicturePage.fileSizeErrorMessage));

        Assert.assertTrue("Size error message is not displayed",
                profilePicturePage.fileSizeErrorMessage.isDisplayed());

        Assert.assertEquals("Incorrect size validation message",
                "Attachment Size Exceeded",
                profilePicturePage.fileSizeErrorMessage.getText().trim());
    }
}