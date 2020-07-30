import Util.WebDriverFactory;
import config.Locators;
import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class OtusTests {

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

    // Пример работы с алертами
    @Test
    public void alertTest() {
        driver.get("https://dwweb.ru/page/js/002_alert_javascript.html");
        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    // Пример теста в котором мы не можем обойтись без неяного ожидания
    @Test
    public void newTest() throws InterruptedException {
        driver.get("https://ng-bootstrap.github.io/#/components/alert/examples");
        clickOnChangeMsg();
        String firstAlertText = getAlertMsg();
        logger.info(firstAlertText);
        Thread.sleep(1000);
        clickOnChangeMsg();
        String secondAlertText = getAlertMsg();
        logger.info(secondAlertText);
        Assert.assertNotEquals(firstAlertText, secondAlertText);
    }

    private void clickOnChangeMsg() {
        driver.findElement(Locators.get("showTimeButton")).click();
    }

    private String getAlertMsg() {

        WebElement alertBox = driver.findElement(Locators.get("alertBox"));
        new WebDriverWait(driver, 4).until(visibilityOf(alertBox));
        return alertBox.getText();
    }

    // 10 Урок - домашнее задане
    @Test
    public void mySecondTest() throws InterruptedException {
        //1. Открыть otus.ru
        driver.get(cfg.otusUrl());
        logger.info("Открыта страница отус");

        //2. Авторизироваться на сайте отус
        signInOtus();

        //3. Войти в личный кабинет
        waitUntilVisible("userIcon");
        driver.get(cfg.otusPersonal());
        logger.info("Открыт личный кабинет пользователя");

        //4. В разделе О себе заполнить поля и добавить не менее двух контактов
        waitUntilVisible("cyrillicName").clear();
        waitUntilVisible("cyrillicName").sendKeys("Евгений");
        waitUntilVisible("cyrillicSurname").clear();
        waitUntilVisible("cyrillicSurname").sendKeys("Колесник");
        waitUntilVisible("latinName").clear();
        waitUntilVisible("latinName").sendKeys("Yevhenii");
        waitUntilVisible("latinSurname").clear();
        waitUntilVisible("latinSurname").sendKeys("Kolesnyk");

        //5. Нажать сохранить
        waitUntilVisible("saveAndContinue").click();

        //6. Открыть https://otus.ru в "чистом браузере"
        driver.quit();
        driver = WebDriverFactory.createNewDriver("Chrome", "test");
        driver.manage().window().maximize();
        driver.get(cfg.otusUrl());
        logger.info("Открыта страница отус");

        //7. Авторизоваться на сайе
        signInOtus();
    }

    private void signInOtus() throws InterruptedException {
        waitUntilVisible("signInAndRegistration").click();
        Thread.sleep(5000);
        waitUntilVisible("emailTextField").sendKeys(cfg.email());
        waitUntilVisible("passwordTextField").sendKeys(cfg.password());
        waitUntilVisible("enterButton").click();
        logger.info("Пользоватль авторизированый");
    }

    static WebElement waitUntilVisible(String webElement) {
        WebElement element = driver.findElement(Locators.get(webElement));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(Locators.get(webElement)));
        return element;
    }

    @Test
    public void openOtusPage() throws InterruptedException {
        driver.get(cfg.otusUrl());
        driver.findElement(By.xpath("//title[contains(text(),'Онлайн‑курсы для профессионалов')]"));
        driver.findElement(Locators.get("signInAndRegistration")).click();
        logger.info("OTUS main page is opened");
    }

    // Пример работы с куками
    @Test
    public void testCookies() {

        driver.get(cfg.otusUrl());

        //driver.manage().addCookie(new Cookie("auth_token", "680B7RmzCkwAOCE1c1Bk7A"));
        //driver.manage().addCookie(new Cookie("auth_token_expires", "1690135916"));
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
}