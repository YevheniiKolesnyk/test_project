package Util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    protected static WebDriver driver;

    public static WebDriver createNewDriver(String webDriverName) {

        WebDriver driver = null;

        if (webDriverName.equalsIgnoreCase("Chrome")) {

            driver = setUpChromeDriver();
            logger.info("set up chrome driver");

        } else if (webDriverName.equalsIgnoreCase("FireFox")) {

            driver = setUpFireFoxDriver();
            logger.info("set up firefox driver");

        }
        return driver;
    }

    public static WebDriver createNewDriver(String webDriverName, String driverOptions) {

        WebDriver driver = null;

        if (webDriverName.equalsIgnoreCase("Chrome")) {

            driver = setUpChromeDriver(driverOptions);
            logger.info("set up chrome driver");

        } else if (webDriverName.equalsIgnoreCase("FireFox")) {

            driver = setUpFireFoxDriver(driverOptions);
            logger.info("set up firefox driver");

        }
        return driver;
    }

    private static WebDriver setUpChromeDriver() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        return driver = new ChromeDriver(options);
    }

    private static WebDriver setUpFireFoxDriver() {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        return driver = new FirefoxDriver(options);
    }

    private static WebDriver setUpChromeDriver(String driverOptions) {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(driverOptions);

        return driver = new ChromeDriver(options);
    }

    private static WebDriver setUpFireFoxDriver(String driverOptions) {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(driverOptions);

        return driver = new FirefoxDriver(options);
    }
}
