package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class ProfilePicturePage extends CommonMethods {

    @FindBy(xpath = "//img[contains(@class,'employee-image')] | //div[contains(@class,'employee-image-wrapper')] | //div[contains(@class,'orangehrm-edit-employee-image')]")
    public WebElement profileAvatar;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement profileUploadInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement uploadButton;

    @FindBy(xpath = "//img[contains(@class,'employee-image') or contains(@src,'profile')]")
    public WebElement uploadedProfileImage;

    @FindBy(xpath = "//*[normalize-space()='File type not allowed']")
    public WebElement fileFormatErrorMessage;

    @FindBy(xpath = "//*[normalize-space()='Attachment Size Exceeded']")
    public WebElement fileSizeErrorMessage;

    public ProfilePicturePage() {
        PageFactory.initElements(driver, this);
    }
}