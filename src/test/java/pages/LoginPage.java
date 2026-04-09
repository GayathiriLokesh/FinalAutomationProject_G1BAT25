package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import static utils.CommonMethods.driver;

public class LoginPage extends CommonMethods {
    @FindBy(name="username")
    public WebElement usernameField;

    @FindBy(name = "password")
    public WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginButton;

    @FindBy(xpath = "//*[normalize-space()='Invalid credentials']")
    public WebElement invalidCredentialsMessage;

    @FindBy(xpath = "//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement usernameRequiredMessage;

    @FindBy(xpath = "//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement passwordRequiredMessage;

    public LoginPage(){
        PageFactory.initElements(driver,this);
    }

}
