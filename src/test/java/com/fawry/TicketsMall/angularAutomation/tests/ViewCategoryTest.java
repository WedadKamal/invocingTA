package com.fawry.TicketsMall.angularAutomation.tests;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddAndUpdateCategoryPage;
import com.fawry.TicketsMall.angularAutomation.pages.ViewCategoryPage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Locale;

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
    @Test(description = "list category| verify that events icon is clickable",priority =0,enabled = false)
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

    @Test(description = "delete category| verify that search by deleted category",priority = 1,enabled = true)
    public void VerifySearchByDeletedCategory()
    {
        test = extent.createTest("36393 --- delete category| verify that search by deleted category");
        Log.test = test;
        Log.startTestCase("36393 --- delete category| verify that search by deleted category");
        CategoryDM backendModelDeletedObj = backendService.getDeletedCategory();
        String DeletedCategory = backendModelDeletedObj.getEnCategoryName();
        ViewCategoryPage DeleteCategoryPageObj = new ViewCategoryPage(driver);
        actualResults =DeleteCategoryPageObj.setSearchCategoryName(DeletedCategory);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While set category name on search text box");
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+GeneralConstants.NO_RESULT_MSG);
        actualResults = DeleteCategoryPageObj.CheckNoResultFoundDisplayed();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertEquals(actualResults, GeneralConstants.NO_RESULT_MSG, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check No ResultFound is Displayed");
        Log.info(" *********  Frontend Assertion passed successfully ********");

    }

    @Test(description = "search category| verify that search by category name doesn't exist",priority = 2,enabled = true)
    public void VerifySearchByCategoryNameDoesnotExist()
    {
        test = extent.createTest("34495 --- search category| verify that search by category name doesn't exist");
        Log.test = test;
        Log.startTestCase("34495 --- search category| verify that search by category name doesn't exist");
        ViewCategoryPage DeleteCategoryPageObj = new ViewCategoryPage(driver);
        String NewAnynoumousName = DeleteCategoryPageObj.CreateAnynomousCatName();
        actualResults =DeleteCategoryPageObj.setSearchCategoryName(NewAnynoumousName);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While set category name on search text box");
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+GeneralConstants.NO_RESULT_MSG);
        actualResults = DeleteCategoryPageObj.CheckNoResultFoundDisplayed();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertEquals(actualResults, GeneralConstants.NO_RESULT_MSG, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check No ResultFound is Displayed");
        Log.info(" *********  Frontend Assertion passed successfully ********");

    }

    @Test(description = "search category| verify that search by category name doesn't exist",priority = 3,enabled = true)
    public void VerifySearchByExistCategoryName()
    {
        test = extent.createTest("34494 --- search category| verify that search by exist category name");
        Log.test = test;
        Log.startTestCase("34494 --- search category| verify that search by exist category name");
        CategoryDM backendModelObj = backendService.getAllCategoryDetails();
     String FirstCatNameFromDB = backendModelObj.getEnCategoryName();
        // String FirstCatNameFromDB = "CategoryUpdatedNameEn103207";
        CategoryDM backendModelObj2 = backendService.getCategoryEventsCount(FirstCatNameFromDB);
        int CatEventsNumber;
        if(backendModelObj2== null){
            CatEventsNumber=0;

        }else {
         CatEventsNumber =backendModelObj2.getCategoryEventsCount();}

        ViewCategoryPage ViewCatPageObj = new ViewCategoryPage(driver);
        actualResults =ViewCatPageObj.setSearchCategoryName(FirstCatNameFromDB);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While sit category name on search text box");
        ArrayList<CategoryDM> AllCatDataFromView= ViewCatPageObj.getViewCategoryData();
        CategoryDM CatDMFromTable =ViewCatPageObj.getCatDMFromViewTable(AllCatDataFromView,FirstCatNameFromDB);
        String CatEvents=String.valueOf(CatEventsNumber);

        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+FirstCatNameFromDB);
        actualResults = CatDMFromTable.getEnCategoryName();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertEquals(actualResults, FirstCatNameFromDB, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check EnCatName is exist in search criteria");
        Log.info("Expected massage = "+"Events ("+CatEvents+")");
        actualResults = CatDMFromTable.getCategory_Events();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertEquals(actualResults, "Events ("+CatEvents+")", GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check catEvents is exist in search criteria");
        Log.info(" *********  Frontend Assertion passed successfully ********");

    }

    @Test(description = "search category| verify that search by category name doesn't exist",priority = 4,enabled = true)
    public void VerifySearchBySensitiveCaseCategoryName()
    {
        test = extent.createTest("34499 --- search category| search icon accept sensitive case");
        Log.test = test;
        Log.startTestCase("34499 --- search category| search icon accept sensitive case");
        CategoryDM backendModelObj = backendService.getAllCategoryDetails();
        String FirstCatNameFromDB = backendModelObj.getEnCategoryName();
        String UpperCaseFirstCatNameFromDB= FirstCatNameFromDB.toUpperCase();

        ViewCategoryPage ViewCatPageObj = new ViewCategoryPage(driver);
        actualResults =ViewCatPageObj.setSearchCategoryName(UpperCaseFirstCatNameFromDB);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While sit category name on search text box");
        ArrayList<CategoryDM> AllCatDataFromView= ViewCatPageObj.getViewCategoryData();
        CategoryDM CatDMFromTable =ViewCatPageObj.getCatDMFromViewTable(AllCatDataFromView,UpperCaseFirstCatNameFromDB);

        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+UpperCaseFirstCatNameFromDB);
        actualResults = CatDMFromTable.getEnCategoryName();
        Log.info("Actual massage = "+ actualResults);
        Assert.assertTrue(actualResults.equalsIgnoreCase(UpperCaseFirstCatNameFromDB), GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check EnCatName is exist in search criteria");
        Log.info(" *********  Frontend Assertion passed successfully ********");

    }

    @Test(description = "search category| verify that search by category name doesn't exist",priority = 5,enabled = true)
    public void VerifySearchByLetterinCategoryName()
    {
        test = extent.createTest("34497 --- search category| verify that user can search by letter");
        Log.test = test;
        Log.startTestCase("34497 --- search category| verify that user can search by letter");
        CategoryDM backendModelObj = backendService.getAllCategoryDetails();
        String FirstCatNameFromDB = backendModelObj.getEnCategoryName();
        char FirstChar =FirstCatNameFromDB.charAt(0);
        String FirstCharFromFirstCatNameFromDB =Character.toString(FirstChar);
        ViewCategoryPage ViewCatPageObj = new ViewCategoryPage(driver);
        actualResults =ViewCatPageObj.setSearchCategoryName(FirstCharFromFirstCatNameFromDB);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While sit category name on search text box");
        ArrayList<CategoryDM> AllCatDataFromView= ViewCatPageObj.getViewCategoryData();
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+"Catogeries Names start with char "+FirstCharFromFirstCatNameFromDB);

        for (int i = 0 ; i< AllCatDataFromView.size(); i++){

            actualResults = AllCatDataFromView.get(i).getEnCategoryName();

            Assert.assertTrue(actualResults.toLowerCase().contains(FirstCharFromFirstCatNameFromDB.toLowerCase()), GeneralConstants.POM_EXCEPTION_ERR_MSG + " While check EnCatName is exist in search criteria");

            Log.info("Actual massage = "+ actualResults);
        }
        Log.info(" *********  Frontend Assertion passed successfully ********");


    }
}
