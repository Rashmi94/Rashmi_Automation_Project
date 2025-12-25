package com.project.test;

import com.project.base.Base;
import com.project.pages.com.project.HomePage;
import com.project.pages.com.project.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends Base {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPages()
    {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    public void verifyHomePageLogo()
    {
       // loginPage.login("Admin","admin123");

    }
}
