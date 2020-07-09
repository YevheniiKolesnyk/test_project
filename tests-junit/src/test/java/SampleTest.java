import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SampleTest {

    private Logger logger = LogManager.getLogger(SampleTest.class);

    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    protected static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");
//        driver = new ChromeDriver(options);

        driver = new ChromeDriver();
        logger.info("driver up and running");
    }

    @Test
    public void myFirstTest(){

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(cfg.googleUrl());

        waitUntilVisible("//input[@type = 'text' and @role = 'combobox']");
        logger.info("google page is opened");

        driver.findElement(By.xpath("//input[@type = 'text' and @role = 'combobox']"))
            .sendKeys("otus");;

        waitUntilClickableAndClick("//div//span[text() = 'otus']", 3);
        waitUntilClickableAndClick("//h3[text() = 'Otus']", 6);

        driver.findElement(By.xpath("//title[contains(text(),'Онлайн‑курсы для профессионалов')]"));
        logger.info("OTUS main page is opened");

    }

    private void waitUntilVisible (String element) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
    }

    private void waitUntilClickableAndClick (String element, int timeOutInSeconds) {
        new WebDriverWait(driver, timeOutInSeconds)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(element))).click();
    }

    @Test
    public void openOtusPage() {
        driver.get(cfg.otusUrl());
        driver.findElement(By.xpath("//title[contains(text(),'Онлайн‑курсы для профессионалов')]"));
        logger.info("OTUS main page is opened");
    }

    @Test
    public void testCookies() {
        driver.get(cfg.otusUrl());

        driver.manage().addCookie(new Cookie("Otus1", "Value1"));

        Cookie cookie = new Cookie("Otus3", "Value3");
        driver.manage().addCookie(cookie);

        logger.info(driver.manage().getCookies());
        logger.info(driver.manage().getCookieNamed("Otus2"));

        driver.manage().deleteAllCookies();

        logger.info(driver.manage().getCookies().size());
        logger.info(driver.manage().getCookieNamed("Otus2"));

        Assert.assertNull(driver.manage().getCookieNamed("Otus2"));
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}