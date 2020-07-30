import Util.WebDriverFactory;
import config.Locators;
import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class GoogleTests {

    private Logger logger = LogManager.getLogger(OtusTests.class);

    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    protected static WebDriver driver;

    @Before
    public void setUp() {
        driver = WebDriverFactory.createNewDriver("Chrome", "test");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void myFirstTest() {

        driver.get(cfg.googleUrl());

        waitUntilVisible("googleSearchField");
        logger.info("google page is opened");

        driver.findElement(Locators.get("googleSearchField")).sendKeys("otus");

        waitUntilClickableAndClick("valueInDropdownSearch", 3);
        waitUntilClickableAndClick("searchResults", 6);

        driver.findElement(Locators.get("signInAndRegistration"));
        logger.info("OTUS main page is opened");
    }

    private void waitUntilClickableAndClick(String webElement, int time) {
        WebElement element = driver.findElement(Locators.get(webElement));
        new WebDriverWait(driver, time)
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    static void waitUntilVisible(String webElement) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(Locators.get(webElement)));
    }
}
