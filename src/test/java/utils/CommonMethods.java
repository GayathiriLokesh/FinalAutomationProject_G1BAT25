package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class CommonMethods extends PageInitializer{
    public static final Logger logger = LogManager.getLogger(CommonMethods.class);
    public static WebDriver driver;
    public void openBrowserAndLaunchApplication(){
        String browser=ConfigReader.read("browser");
        logger.info("Launching browser: {}", browser);
        switch(browser){
            case "Chrome":
                ChromeOptions options=new ChromeOptions();
                options.addArguments("--headless");
                driver=new ChromeDriver(options);
                break;
            case "FireFox":
                driver = new FirefoxDriver();
                break;
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "Safari":
                driver = new SafariDriver();
                break;
            default:
                logger.error("Invalid browser name: {}", browser);
                throw new RuntimeException("Invalid browser name");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        String url=ConfigReader.read("url");
        driver.get(url);
        logger.info("Navigated to URL: {}", url);

    }
    public void closeBrowser(){
        if(driver!=null){
            logger.info("Closing browser");
            driver.quit();
        }
    }
    public WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
    }
    public void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
            }
     public void sendText(String text,WebElement element){
        waitForVisibility(element);
         logger.info("Entering text: {}", text);
        element.clear();
        element.sendKeys(text);
     }
    public void waitForElementToBeClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    public void click(WebElement element){
        waitForElementToBeClickable(element);
        logger.info("Clicking on element");
        element.click();
    }
    public String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText().trim();
    }
    public boolean isElementDisplayed(WebElement element) {
        try {
            waitForVisibility(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTimeStamp(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
    public byte[] takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        logger.info("Capturing screenshot: {}", fileName);

        byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
        File source = ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(source, new File(Constants.SCREENSHOT_FILEPATH + fileName + "_"
                    + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png"));
        } catch (IOException e) {
            logger.error("Screenshot failed", e);
                    }

        return bytes;
    }

       public void selectDynamicOption(String optionText) {

           logger.info("Selecting dropdown option: {}", optionText);
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By optionsLocator = By.xpath("//div[@role='option']//span");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));

        List<WebElement> options = driver.findElements(optionsLocator);

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionText)) {
                logger.info("Option found and selected: {}", optionText);
                click(option);
                return;
            }
        }
           logger.error("Dropdown option NOT found: {}", optionText);
        throw new RuntimeException("Dropdown option not found: " + optionText);
    }
    public void safeClick(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            element.click();
        } catch (Exception e) {
            // fallback using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }
    public void selectEmployeeFromAutoComplete(WebElement input, String value) {

        logger.info("Selecting employee from autocomplete: {}", value);
        input.clear();
        sendText(value, input);

        By loading = By.xpath("//*[contains(text(),'Searching')]");
        By options = By.xpath("//div[@role='option']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // wait for search to complete
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        } catch (Exception ignored) {}

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(options, 0));

        List<WebElement> list = driver.findElements(options);

        for (WebElement e : list) {
            String text = e.getText().trim();

            if (text.equalsIgnoreCase(value) ||
                    text.toLowerCase().contains(value.toLowerCase())) {
                logger.info("Employee selected: {}", text);
                safeClick(e);
                return;
            }
        }
        logger.error("Employee NOT found in autocomplete: {}", value);
        throw new RuntimeException("AutoComplete value not found: " + value);
    }
    public void selectEmployeeFromVisibleSuggestions(String employeeName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        By loadingText = By.xpath("//*[contains(text(),'Searching')]");
        By optionsLocator = By.xpath("//div[@role='option'] | //div[@role='listbox']//div");

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingText));
        } catch (Exception e) {
            // ignore if loading disappears quickly
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='listbox']")));

        List<WebElement> options = driver.findElements(optionsLocator);

        for (WebElement option : options) {
            String text = option.getText().trim();

            if (!text.isEmpty() &&
                    (text.equalsIgnoreCase(employeeName.trim())
                            || text.toLowerCase().contains(employeeName.trim().toLowerCase()))) {
                option.click();
                return;
            }
        }

        throw new RuntimeException("Employee suggestion not found: " + employeeName);
    }

    public void typeSlowly(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();

        for (char ch : text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Typing was interrupted", e);
            }
        }

        element.sendKeys(Keys.TAB);
    }
    public void waitForSearchResultsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By rowsLocator = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
        By noRecordsLocator = By.xpath("//span[normalize-space()='No Records Found']");

        wait.until(driver ->
                driver.findElements(rowsLocator).size() > 0
                        || driver.findElements(noRecordsLocator).size() > 0
        );
    }
    public void waitForLoaderToDisappear() {
        List<WebElement> loaders = driver.findElements(By.cssSelector(".oxd-form-loader"));

        if (!loaders.isEmpty()) {
            getWait().until(ExpectedConditions.invisibilityOfAllElements(loaders));
        }
    }
    public void selectDropdownOption(String value) {
        By optionLocator = By.xpath("//span[text()='" + value + "']");
        WebElement option = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(optionLocator)
        );
        click(option);

    }
    public void clearAndType(WebElement element, String text) {
        waitForElementToBeClickable(element);
        logger.info("Clearing and typing text: {}", text);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

}
