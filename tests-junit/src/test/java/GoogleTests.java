import config.Locators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class GoogleTests extends BaseTest {

    private Logger logger = LogManager.getLogger(GoogleTests.class);

    @Test
    public void googleTest() {

        driver.get(cfg.googleUrl());

        waitUntilVisible("googleSearchField");
        logger.info("google page is opened");

        driver.findElement(Locators.get("googleSearchField")).sendKeys("otus");

        waitUntilClickableAndClick("valueInDropdownSearch", 3);
        waitUntilClickableAndClick("searchResults", 6);

        driver.findElement(Locators.get("signInAndRegistration"));
        logger.info("OTUS main page is opened");
    }
}