package com.project.pages.com.project;

import com.project.actiondriver.ActionDriver;
import com.project.base.Base;
import org.openqa.selenium.By;

public class HomePage {
    private ActionDriver actionDriver;

    public HomePage()
    {
        this.actionDriver = Base.getActionDriver();
    }

    private By shopNameHeader = By.xpath("//h1[text()='Shop Name']");

    public boolean isShopNameVisibile()
    {
        return actionDriver.isElementDisplayed(shopNameHeader);
    }





}
