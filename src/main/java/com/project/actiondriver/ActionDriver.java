package com.project.actiondriver;

import com.project.base.Base;
import com.project.utilities.ExtentManager;
import com.project.utilities.LoggingManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ActionDriver{

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggingManager.getLogging(ActionDriver.class);

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(Base.getProp().getProperty("explicitWait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    public void waitElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("element not clickable" + e.getMessage());
        }

    }

    public void waitElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("element not visible" + e.getMessage());
        }
    }

    public void click(By by) {
        try {
            applyBorder(by,"green");
            getElementDescription(by);
            waitElementToBeClickable(by);
            driver.findElement(by).click();
            ExtentManager.logStep("Clicked an elem "+getElementDescription(by));
        } catch (Exception e) {
            applyBorder(by,"red");
            logger.error("element is not clicked" + e.getMessage());
        }
    }

    public void enterText(By by, String text) {
        try {
            applyBorder(by,"green");
            getElementDescription(by);
            waitElementToBeVisible(by);
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(text);
        } catch (Exception e) {
            applyBorder(by,"red");
            logger.error("text not entered" + e.getMessage());
        }
    }

    public String getText(By by) {
        try {
            waitElementToBeVisible(by);
            applyBorder(by,"green");
            ExtentManager.logStep("getting the text of element :"+by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            applyBorder(by,"red");
            return "";
        }
    }

    public void compareText(By by, String expectedText) {
        try {
            waitElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if (actualText.equals(expectedText)) {
                logger.info("Texts are matching actual text :" + actualText + "expected text :" + expectedText);
                applyBorder(by,"green");
                ExtentManager.logPass(driver,"Compare text","Texts are matching");
            }
        } catch (Exception e) {
            ExtentManager.logFailure(driver,"Compare text","Texts are not matching");
            applyBorder(by,"red");
            logger.error("unable to compare text");
        }
    }

    public boolean isElementDisplayed(By by) {
        try {
            waitElementToBeVisible(by);
            applyBorder(by,"green");
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            applyBorder(by,"red");
            logger.error("element not displayed" + e.getMessage());
        }
        return false;
    }

    public void scrollToElement(By by)
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement ele = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);",ele);
            applyBorder(by,"green");
        } catch (Exception e) {
            applyBorder(by,"red");
            logger.error("failed to scroll to element"+e.getMessage());
        }
    }

    public void waitForPageToLoad(int timeOut)
    {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOut)).until(WebDriver ->((JavascriptExecutor)WebDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.error("failed to wait for page to load"+e.getMessage());
        }
    }

    public boolean compareText(By by,String actualText, String expectedText)
    {
        if(actualText.equalsIgnoreCase(expectedText)){
            logger.info("Texts are matching actual text :" + actualText + "expected text :" + expectedText);
            applyBorder(by,"green");
            ExtentManager.logPass(driver,"Comparing text expected : "+expectedText+". actual text : "+actualText,"Texts are matching");
            return true;
        }
        else {
            applyBorder(by,"red");
            logger.error("unable to compare text");
            ExtentManager.logFailure(driver,"Comparing text expected : "+expectedText+". sactual text : "+actualText,"Texts are not matching");
            return false;
        }

    }

    public String getElementDescription(By locator)
    {
        if(driver==null)
            return "driver is null";
        if(locator==null)
            return "locator is null";

        WebElement elem = driver.findElement(locator);
        String name = elem.getDomAttribute("name");
        String id = elem.getDomAttribute("id");
        String className = elem.getDomAttribute("class");
        String placeHolder = elem.getDomAttribute("placeholder");
        String text = elem.getText();
        try {
            if(isElementNullOrEmpty(name))
                return "element with name is : "+name;
            else if(isElementNullOrEmpty(id))
                return "element id is : "+id;
            else if(isElementNullOrEmpty(className))
                return "element class name is : "+className;
            else if(isElementNullOrEmpty(text))
                return "element text is : "+truncateElemDesc(text,50);
            else if(isElementNullOrEmpty(placeHolder))
                return "element place holder is : "+placeHolder;
            else
                return "Element located using "+locator.toString();
        } catch (Exception e) {
            logger.error("no valid attribute value exists");
        }
        return null;


    }

    public boolean isElementNullOrEmpty(String attributeValue)
    {
        return attributeValue != null && !attributeValue.isEmpty();
    }

    public String truncateElemDesc(String text,int maxlength)
    {
        if(text!=null && !text.isEmpty() && maxlength>0)
            return text.substring(0,maxlength)+"...";
        return null;
    }

    public void applyBorder(By by, String color)
    {
        try {
            WebElement elem = driver.findElement(by);
            String script = "arguments[0].style.border='3px solid "+color+"'";
            JavascriptExecutor js = (JavascriptExecutor) Base.getDriver();
            js.executeScript(script,elem);
            logger.info("border applied using js for elem "+  getElementDescription(by));
        } catch (Exception e) {
            logger.warn("failed to apply border "+getElementDescription(by));
            throw new RuntimeException(e);
        }

    }


}
