import Util.WebDriverFactory;
import config.Locators;
import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    protected static WebDriver driver;

    @Before
    public void setUp() {
        driver = WebDriverFactory.createNewDriver("Chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    void waitUntilClickableAndClick(String webElement, int time) {
        WebElement element = driver.findElement(Locators.get(webElement));
        new WebDriverWait(driver, time)
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    void waitUntilVisible(String webElement) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(Locators.get(webElement)));
    }

    WebElement waitUntilElementVisible(String webElement) {
        WebElement element = driver.findElement(Locators.get(webElement));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(Locators.get(webElement)));
        return element;
    }

    void hoverOnAndClick(String hover, String hoverAndClick) {
        WebElement hoverOn = driver.findElement(Locators.get(hover));
        WebElement hoverOnAndClick = driver.findElement(Locators.get(hoverAndClick));

        Actions builder = new Actions(driver);
        builder.moveToElement(hoverOn).moveToElement(hoverOnAndClick).click().build().perform();
    }

    void waitUntilClickableAndClick(String webElement) {
        WebElement element = driver.findElement(Locators.get(webElement));
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
