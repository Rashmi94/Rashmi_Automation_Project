package com.project.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap();

    //initializing the extent report
    public static synchronized ExtentReports getReporter()
    {
        if(extentReports==null)
        {
            String reportPath = System.getProperty("user.dir")
                + "/build/reports/extent/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Rashmi UI Project");
            spark.config().setTheme(Theme.DARK);

            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("java version",System.getProperty("java.version"));
            extentReports.setSystemInfo("os name",System.getProperty("os.name"));
            extentReports.setSystemInfo("os version",System.getProperty("os.version"));
            extentReports.setSystemInfo("username",System.getProperty("user.name"));
        }
        return extentReports;
    }

    //start the test
    public static synchronized ExtentTest startTest(String testName)
    {
        ExtentTest test = getReporter().createTest(testName);
        extentTest.set(test);
        return extentTest.get();
    }

    //end the test
    public static synchronized void endTest()
    {
        getReporter().flush();
    }


    //get the current thread testname
    public static synchronized ExtentTest getTest()
    {
        return extentTest.get();
    }

    //get name of the current test
    public static String getTestName()
    {
        ExtentTest ex = getTest();
        if(ex!=null)
        {
            return ex.getModel().getName();
        }
        else
            return "no test is currently active for the given thread";
    }
    //logging step
    public static void logStep(String logMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            test.info(logMessage);
        }
    }

    //logging on pass
    public static void logPass(WebDriver driver, String logMessage, String screenshotMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            String colorMsg = String.format("<span style='color:green;'>%s</span>",logMessage);
            test.pass(colorMsg);
            attachScreenshotToReport(driver,screenshotMessage);
        }
    }

    //logging on pass API
    public static void logPassAPI(String logMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            String colorMsg = String.format("<span style='color:green;'>%s</span>",logMessage);
            test.pass(colorMsg);
        }
    }

    //logging on failure
    public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            String colorMsg = String.format("<span style='color:red;'>%s</span>",logMessage);
            test.fail(colorMsg);
            attachScreenshotToReport(driver,screenshotMessage);
        }
    }

    //logging on failure for API
    public static void logFailureAPI(String logMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            String colorMsg = String.format("<span style='color:red;'>%s</span>",logMessage);
            test.fail(colorMsg);
        }
    }

    //logging on skip
    public static void logSkip(WebDriver driver, String logMessage, String screenshotMessage)
    {
        ExtentTest test = getTest();
        if(test != null) {
            String colorMsg = String.format("<span style='color:yellow;'>%s</span>",logMessage);
            test.skip(colorMsg);
        }
    }

    //take screenshot with date and time in file
    public static synchronized String getScreenshot(WebDriver driver,String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String destPath = System.getProperty("user.dir")+"/src/test/resources/screenshots/"+screenshotName+"_"+timeStamp+".png";
        File dest = new File(destPath);
        FileUtils.copyFile(src, dest);
        return convertToBase64Format(dest);
    }


    public static String convertToBase64Format(File ScreenshotFile) throws IOException {
        String base64Format = "";
        byte[] byteArrayFile = FileUtils.readFileToByteArray(ScreenshotFile);
        base64Format = Base64.getEncoder().encodeToString(byteArrayFile);
        return base64Format;
    }

    public static synchronized void attachScreenshotToReport(WebDriver driver, String message) {
        String base64Format = null;
        try {
            base64Format = getScreenshot(driver,getTestName());
        } catch (IOException e) {
            getTest().fail("Failed to  capture screenshot"+message);
            e.printStackTrace();
        }
        getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(base64Format).build());

    }

    //register webdriver for current thread
    public static void registerDriver(WebDriver driver)
    {
        driverMap.put(Thread.currentThread().getId(),driver);
    }

}
