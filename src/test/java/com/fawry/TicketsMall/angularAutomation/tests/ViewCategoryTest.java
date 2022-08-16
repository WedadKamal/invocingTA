package com.fawry.TicketsMall.angularAutomation.tests;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddAndUpdateCategoryPage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ViewCategoryTest extends BaseTest{

    String actualResults;
    @BeforeClass(alwaysRun = true)
    public void navigateToVenuePage(){
        String navigatedToPageSuccessfully = homepage.navigateToVenuePage();
        Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Venue Page successfully");
    }
    @BeforeMethod(alwaysRun = true)
    public void navigateToListCategoryPage() {
        Assert.assertEquals(homepage.getWelcomeMsg(), "Welcome", "BLOCKING ISSUE - CAN NOT LOGIN TO APPLICATION");
        driver.navigate().refresh();
        String navigatedToPageSuccessfully = homepage.navigateToCategoryListPage();
        Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to List Category page successfully");
    }
    @Test(description = "list category| verify that events icon is clickable",priority =0,enabled = true)
    public void verifyEventsIconIsClickable(){
        //Create extent test to be logged in report using test case title
        test = extent.createTest("34502 --- list category| verify that events icon is clickable");
        Log.test = test;
        Log.startTestCase("34502 --- list category| verify that events icon is clickable");
        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        String EnCatNameFromView = updateCategoryPageObj.getFirstEnNameCategoryFromView();
        actualResults = updateCategoryPageObj.checkEventsClickable();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Events Icon.");
        CategoryDM backendModel = backendService.getCategoryDetails(EnCatNameFromView);
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ GeneralConstants.EXPECTED_CATEGORY_WITH_EVENTS_URL+backendModel.getCategoryCode());
        actualResults = updateCategoryPageObj.getCategoryCurrentUrl();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertEquals(actualResults, GeneralConstants.EXPECTED_CATEGORY_WITH_EVENTS_URL+backendModel.getCategoryCode(), GeneralConstants.Expected_ERR_MSG );

    }
}
