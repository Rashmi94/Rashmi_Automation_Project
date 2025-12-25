package com.project.listners;

import com.project.base.Base;
import com.project.utilities.ExtentManager;
import com.project.utilities.RetryAnalyzer;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener , IAnnotationTransformer {


    //triggered when test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.startTest(testName);
        ExtentManager.logStep("Test started : " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        if (result.getTestClass().getName().toLowerCase().contains("api"))
            ExtentManager.logPassAPI("Test completed successfully\n" + " Test Ends : " + testName);
        else
            ExtentManager.logPass(Base.getDriver(), "Test completed successfully\n" + " Test Ends : " + testName, "passed");
    }


    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String failure = result.getThrowable().getMessage();
        if (result.getTestClass().getName().toLowerCase().contains("api"))
            ExtentManager.logFailureAPI("Test failed \n" + "Test Ends :" + testName);
        else {
            ExtentManager.logFailure(Base.getDriver(), "Test failed \n" + "Test Ends :" + testName, "failed");
            ExtentManager.logStep(failure);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testClassName = result.getTestClass().getName();
        if (!testClassName.toLowerCase().contains("api")) {
            ExtentManager.logSkip(Base.getDriver(), "Test skipped", "Test ends : " + testName);
        } else {
            ExtentManager.logStep("Test skipped: " + testName);
        }
    }

    //triggered when suite starts
    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getReporter();
    }

    //triggered when suite ends
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.endTest();
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
