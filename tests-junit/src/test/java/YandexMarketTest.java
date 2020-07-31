import config.Locators;
import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class YandexMarketTest extends BaseTest {

    private Logger logger = LogManager.getLogger(YandexMarketTest.class);

    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    protected static WebDriver driver;

    @Test
    public void yaMarketTest() {

        // Открыть в Chrome сайт Яндекс.Маркет - раздел "Мобильные телефоны"
        driver.get(cfg.yaMarket());
        logger.info("Открыта страница " + cfg.yaMarket());

        // Отфильтровать список товаров: Samsung и Xiaomi
        waitUntilClickableAndClick("buttonKatalogTovarov");
        waitUntilVisible("buttonElektronika");
        hoverOnAndClick("buttonElektronika", "buttonMobilnyyeTelefony");
        waitUntilClickableAndClick("buttonPokazatVso");
        waitUntilClickableAndClick("checkSamsung");
        waitUntilClickableAndClick("checkBoxXiaomi");

        // Отсортировать список товаров по цене (от меньшей к большей)

        // Добавить первый в списке Samsung
        List<WebElement> listOfSamsung = driver.findElements(Locators.get("buttonsSravnitSamsung"));
        listOfSamsung.get(0).click();
        waitUntilVisible("buttonSravnit");

        // Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"

        // Добавить первый в списке Xiaomi
        List<WebElement> listOfXiaomi = driver.findElements(Locators.get("buttonsSravnitXiaomi"));
        listOfXiaomi.get(0).click();
        waitUntilVisible("buttonSravnit");

        // Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"

        // Перейти в раздел Сравнение
        waitUntilClickableAndClick("buttonSravnit");

        // Проверить, что в списке товаров 2 позиции

        // Нажать на опцию "все характеристики"
        waitUntilClickableAndClick("buttonVseKharakteristiki");

        // Проверить, что в списке характеристик появилась позиция "Операционная система"
        Assert.assertFalse(isOperatsionnayaSistemaDisplayed());

        // Нажать на опцию "различающиеся характеристики"
        waitUntilClickableAndClick("buttonRazlichayushchiyesyaKharakteristiki");

        // Проверить, что позиция "Операционная система" не отображается в списке характеристик
        Assert.assertFalse(isOperatsionnayaSistemaDisplayed());
    }

    private boolean isOperatsionnayaSistemaDisplayed() {
        return driver.findElement(Locators.get("OperatsionnayaSistema")).isDisplayed();
    }
}