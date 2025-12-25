package com.project.pages.com.project;

import com.project.actiondriver.ActionDriver;
import com.project.base.Base;
import org.openqa.selenium.By;

public class LoginPage {
    private ActionDriver actionDriver;

    public LoginPage()
    {
        this.actionDriver = Base.getActionDriver();
    }

    private By userName = By.name("username");
    private By password = By.name("password");
    private By termsAndConditions = By.name("terms");
    private By signInButton = By.name("signin");
    private By errorMsg = By.xpath("//div[contains(@class,'alert-danger')]");

    public void login(String username, String pwd)
    {
        actionDriver.enterText(userName,username);
        actionDriver.enterText(password,pwd);
        actionDriver.click(termsAndConditions);
        actionDriver.click(signInButton);
        actionDriver.waitForPageToLoad(3);
    }

    public String getErrorMsg()
    {
        return actionDriver.getText(errorMsg);
    }

    public boolean isErrorMsgDisplayed()
    {
        if(actionDriver.isElementDisplayed(errorMsg))
            return verifyErrorMsg(actionDriver.getText(errorMsg));
        else
            return false;
    }

    public boolean verifyErrorMsg(String expectedMsg)
    {
        return actionDriver.compareText(errorMsg,getErrorMsg(),expectedMsg);
    }

}
