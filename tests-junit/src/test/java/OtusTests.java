import Util.WebDriverFactory;
import com.google.common.io.Files;
import config.Locators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class OtusTests extends BaseTest {

    private Logger logger = LogManager.getLogger(OtusTests.class);

    // Пример работы со скриншотами
    @SuppressWarnings("UnstableApiUsage")
    @Test
    public void screenShotTest() {
        driver.get(cfg.otusUrl());
        logger.info("Открыта страница отус");

        waitUntilVisible("signInAndRegistration");

        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tempFile, new File("screenshots/otus_main_page_screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void aShotScreenshotTest() throws IOException {
//        Screenshot imageScreenshot = new AShot()
//                .shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
//        ImageIO.write(imageScreenshot.getImage(), "png", new File("pathname"));
//        File f = new File("pathname");
//    }

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
    public void threadSleepTest() throws InterruptedException {
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
    public void otusTest() throws InterruptedException {
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
        waitUntilElementVisible("cyrillicName").clear();
        waitUntilElementVisible("cyrillicName").sendKeys("Евгений");
        waitUntilElementVisible("cyrillicSurname").clear();
        waitUntilElementVisible("cyrillicSurname").sendKeys("Колесник");
        waitUntilElementVisible("latinName").clear();
        waitUntilElementVisible("latinName").sendKeys("Yevhenii");
        waitUntilElementVisible("latinSurname").clear();
        waitUntilElementVisible("latinSurname").sendKeys("Kolesnyk");

        //5. Нажать сохранить
        waitUntilElementVisible("saveAndContinue").click();

        //6. Открыть https://otus.ru в "чистом браузере"
        driver.quit();
        setUp();
        
        driver.get(cfg.otusUrl());
        logger.info("Открыта страница отус");

        //7. Авторизоваться на сайе
        signInOtus();
    }

    @Test
    public void openOtusPage() {
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

    private void signInOtus() {
        waitUntilElementVisible("signInAndRegistration").click();
        waitUntilVisible("emailTextField");
        waitUntilElementVisible("emailTextField").sendKeys(cfg.email());
        waitUntilElementVisible("passwordTextField").sendKeys(cfg.password());
        waitUntilElementVisible("enterButton").click();
        logger.info("Пользоватль авторизированый");
    }
}