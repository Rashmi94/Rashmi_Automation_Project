package com.project.test;

import com.project.base.Base;
import com.project.pages.com.project.HomePage;
import com.project.pages.com.project.LoginPage;
import com.project.utilities.DataProvidersUtility;
import com.project.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends Base {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPages()
    {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test(dataProvider = "validLoginCreds_DP",dataProviderClass = DataProvidersUtility.class)
    public void verifyLogin(String un, String pwd)
    {
        //ExtentManager.startTest("verifyLogin");
       // loginPage.login(getProp().getProperty("username"),getProp().getProperty("password"));
        loginPage.login(un,pwd);
        //ExtentManager.logStep("navigating to url and logging in");
        Assert.assertTrue(homePage.isShopNameVisibile());
        ExtentManager.logStep("Verify in the home page shop name is visible");
    }

    @Test(dataProvider = "inValidLoginCreds_DP",dataProviderClass = DataProvidersUtility.class)
    public void verifyInValidLogin(String un, String pwd)
    {
        //ExtentManager.startTest("verifyInValidLogin");
        loginPage.login(un,pwd);
        //ExtentManager.logStep("verify logging in with invalid username and pwd");
        //ExtentManager.logStep("asserting the error message");
        Assert.assertTrue(loginPage.isErrorMsgDisplayed());

    }

    @Test
    public void verifySkipLogin()
    {
        throw new SkipException("test skipped");

    }
}
