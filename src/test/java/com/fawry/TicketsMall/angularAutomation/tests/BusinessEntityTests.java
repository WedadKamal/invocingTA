package com.fawry.TicketsMall.angularAutomation.tests;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class BusinessEntityTests extends BaseTest{

    ArrayList addAccountTestData = new ArrayList();

    @BeforeMethod(alwaysRun = true)
    public void navigateToMyAccountsPage() {
      Assert.assertEquals(homepage.getWelcomeMsg(), "Welcome", "BLOCKING ISSUE - CAN NOT LOGIN TO APPLICATION");
      String navigatedToPageSuccessfully = homepage.navigateToAddBusinessEntityPage();
      Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Accounts page successfully");
    }

    @Test(description = "Validate adding new Business Entity functionalities",priority = 0  ,enabled = true)
    public void addNewBusinessEntity() {

        int s =2+3;
    }
}
