package com.project.base;

import com.project.actiondriver.ActionDriver;
import com.project.utilities.ExtentManager;
import com.project.utilities.LoggingManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.io.FileInputStream;

public class Base {
    protected static Properties prop;
    /*protected static WebDriver driver;
    private static ActionDriver actionDriver;*/
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    private static final Logger logger = LoggingManager.getLogging(Base.class);

   /* public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;

    }*/

    public static WebDriver getDriver() {
        if (driver.get() == null)
            throw new IllegalStateException("WebDriver not initialized");
        else
            return driver.get();
    }

    public static Properties getProp() {
        return prop;
    }

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        logger.info("Properties file loaded");
        //ExtentManager.getReporter();
    }

    private void launchBrowser() {
        String browser = prop.getProperty("browser");
        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                //driver = new ChromeDriver();
                driver.set(new ChromeDriver(options));
                ExtentManager.registerDriver(getDriver());
                logger.info("thread id is : " + Thread.currentThread().getId());
                logger.info("chrome driver launched");
                break;
            case "firefox":
                //driver = new FirefoxDriver();
                driver.set(new FirefoxDriver());
                ExtentManager.registerDriver(getDriver());
                logger.info("Fire fox driver launched");
                break;
            default:
                throw new IllegalArgumentException("Browser not supported");
        }
    }

    private void configureBrowser() {
        //add implicit wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //maximize the window
        driver.get().manage().window().maximize();
        logger.info("browser maximized");

    }

    @BeforeMethod
    public synchronized void setup(java.lang.reflect.Method method) {
        launchBrowser();
        configureBrowser();
        //launch url
        try {
            driver.get().get(prop.getProperty("url"));
        } catch (Exception e) {
            throw new RuntimeException("failed to navigate to URL");
        }
        logger.info("webdriver initialized");

        //initialize action driver once
        /*if(actionDriver==null) {
            actionDriver = new ActionDriver(driver);
            logger.info("Action driver initialized");
        }*/

        actionDriver.set(new ActionDriver(driver.get()));
        logger.info("thread is id : " + Thread.currentThread().getId());


    }

    public static ActionDriver getActionDriver() {
        return actionDriver.get();
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null)
            try {
                driver.get().quit();
                logger.info("driver closed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        /*driver=null;
        actionDriver=null;*/
        driver.remove();
        actionDriver.remove();
    }

   /* @AfterSuite(alwaysRun = true)
    public void flushReports() {
        ExtentManager.endTest();
    }*/

}
