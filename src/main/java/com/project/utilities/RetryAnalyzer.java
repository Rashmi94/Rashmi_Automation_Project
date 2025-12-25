package com.project.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int count=0;
    int maxRetryCount=3;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(count<maxRetryCount) {
            count++;
            return true;
        }
        return false;
    }
}
