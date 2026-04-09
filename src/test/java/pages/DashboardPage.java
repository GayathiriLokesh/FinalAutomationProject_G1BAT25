package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class DashboardPage extends CommonMethods {
    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    public WebElement dashboardHeader;

    @FindBy(xpath = "//span[normalize-space()='PIM']")
    public WebElement pimMenu;

    @FindBy(xpath = "//span[normalize-space()='Admin']")
    public WebElement adminMenu;

    @FindBy(xpath = "//span[normalize-space()='My Info']")
    public WebElement myInfoMenu;

    public DashboardPage() {
        PageFactory.initElements(driver, this);
    }
}
