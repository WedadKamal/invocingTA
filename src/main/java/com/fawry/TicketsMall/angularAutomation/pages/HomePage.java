package com.fawry.TicketsMall.angularAutomation.pages;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends MainPage{
    //invoke parent's constructor
    public HomePage(WebDriver driver, NgWebDriver ngWebDriver)
    {
        super(driver, ngWebDriver);
    }

    // Initialize web elements
    @FindBy(tagName= "small")
    WebElement welcomeMsg;
    @FindBy(xpath= "//button/span[contains(text(), 'Sign out')]")
    WebElement signOutBtn;

    @FindBy(xpath= "//*[@role='menubar']/li[not(contains(@class, 'p-hidden'))]")
    WebElement businessEntityDropdownMenuClass;

    @FindBy(xpath= "//*[text()='Business Entities']")
    WebElement businessEntityDropdownMenu;

    @FindBy(xpath= "//*[text()='View Business Entities']")
    WebElement viewBusinessEntityMenuItem;

    @FindBy(xpath= "//span[text()='Add Bussiness Entity']")
    WebElement addBusinessEntityMenuItem;



//    list page's actions

    //get welcome message
    public String getWelcomeMsg()
    {
        String welocmeMsg = "";
        try {
             welocmeMsg = welcomeMsg.getText();
        }
        catch (Exception e)
        {
            Log.error("Error occured in " + new Object() {}
                    .getClass().getName() + "." + new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return welocmeMsg;
    }

    public String logout()
    {
        try {
            welcomeMsg.click( );
            signOutBtn.click();
        }
        catch (Exception e)
        {
            Log.error("Error occured in " + new Object() {}
                    .getClass().getName() + "." + new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return GeneralConstants.SUCCESS;
    }


    public void selectBusinessEntityMenuLink()
    {
        if(!businessEntityDropdownMenuClass.getAttribute("class").equalsIgnoreCase("p-menuitem-active"))
            businessEntityDropdownMenu.click();
    }

    public String navigateToBusinessEntityListPage()
    {
        try
        {
            Log.info("Navigate to Business Entity list page" );
            selectBusinessEntityMenuLink();
            viewBusinessEntityMenuItem.sendKeys(Keys.RETURN);
        } catch (Exception e) {
            Log.error("Error occured in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return GeneralConstants.SUCCESS;
    }

    public String navigateToAddBusinessEntityPage()
    {
        try {
            Log.info("Navigate to Add Business Entity Page" );
            selectBusinessEntityMenuLink();
            addBusinessEntityMenuItem.click();
        } catch (Exception e) {
            Log.error("Error occured in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;        }
        return GeneralConstants.SUCCESS;
    }



}
