package com.fawry.TicketsMall.angularAutomation.pages;

import com.fawry.TicketsMall.angularAutomation.utils.Log;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends MainPage {
    //invoke parent's constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Initialize web elements
    @FindBy(tagName = "small")
    WebElement welcomeMsg;

    @FindBy(xpath = "//button/span[contains(text(), 'Sign out')]")
    WebElement signOutBtn;

    @FindBy(xpath = "//span[text()='Venues']//ancestor::li")
    WebElement venuesLiDropdownMenu;

    @FindBy(xpath = "//span[text()='Venues']")
    WebElement venuesDropdownMenu;

    @FindBy(xpath = "//span[contains(text(),'List Venues')]")
    WebElement listVenueMenuItem;

    @FindBy(xpath = "//span[contains(text(),'Add new Venue')]")
    WebElement addVenueMenuItem;

    //get welcome message
    public String getWelcomeMsg() {
        String welcomeMsgStr = "";
        try {
            welcomeMsgStr = welcomeMsg.getText();
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return GeneralConstants.FAILED;
        }
        return welcomeMsgStr;
    }

    public String logout() {
        try {
            welcomeMsg.click();
            signOutBtn.click();
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
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

    public void selectVenuesMenuLink() throws InterruptedException {
        scrollIntoView(venuesDropdownMenu);
        if (!venuesLiDropdownMenu.getAttribute("class").equalsIgnoreCase("p-menuitem-active"))
            venuesDropdownMenu.click();
    }

    public String navigateToListVenuesPage() {
        try {
            Log.info("Navigate to list Venues page");
            selectVenuesMenuLink();
            listVenueMenuItem.sendKeys(Keys.RETURN);

        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
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

    public String navigateToAddVenuePage() {
        try {
            Log.info("Navigate to add venue page");
            selectVenuesMenuLink();
            addVenueMenuItem.click();
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
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



}
