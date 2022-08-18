package com.fawry.TicketsMall.angularAutomation.tests;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.constants.excelIndices.addCategoryExcelIndices;
import com.fawry.TicketsMall.angularAutomation.constants.excelIndices.deleteCategoryExcelIndices;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddAndUpdateCategoryPage;
import com.fawry.TicketsMall.angularAutomation.pages.ViewCategoryPage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class DeleteCategoryTest extends BaseTest{

    ArrayList DeleteCategoryTestData = new ArrayList();

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

    @Test(description = "Delete Category",priority = 0,dataProvider = "deleteCategoryDP",enabled = true)
    public void DeleteCategory(CategoryDM CatDMObj)
    {
        test = extent.createTest(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());
        ViewCategoryPage DeleteCategoryPageObj = new ViewCategoryPage(driver);
        ArrayList<CategoryDM> CategoryDMs = DeleteCategoryPageObj.getViewCategoryData();
        String CatEnameFromView = DeleteCategoryPageObj.getCatEnNameFromViewWithoutEvents(CategoryDMs);
        Log.info("category eng name to be clicked"+ CatEnameFromView);
        actualResults = DeleteCategoryPageObj.ClickDeleteButtonToCategoryWithoutEvents();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Delete Category Icon");
        actualResults = DeleteCategoryPageObj.YesConfirmationDeleteMessage();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Yes on Confirmation Delete Category");

        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        actualResults = updateCategoryPageObj.getAllErrMsgsForCategory(CatDMObj.getErrType(),GeneralConstants.TESTING_IMAGE);
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ CatDMObj.getExpectedMessage());
        Assert.assertEquals(actualResults, CatDMObj.getExpectedMessage(), GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");

        if (actualResults.contains(GeneralConstants.SUCCESS))
        {
            assertDeleteCategory(CatEnameFromView,CatDMObj);
        }




    }

    @Test(description = "delete category| verify that category has events couldn't be delete",priority = 1,enabled = true)
    public void VerifyCategoryHasEventsCannotBeDeleted()
    {
        test = extent.createTest("34493 --- delete category| verify that category has events couldn't be delete");
        Log.test = test;
        Log.startTestCase("34493 --- delete category| verify that category has events couldn't be delete");
        ViewCategoryPage DeleteCategoryPageObj = new ViewCategoryPage(driver);
        ArrayList<CategoryDM> CategoryDMs = DeleteCategoryPageObj.getViewCategoryData();
        String CatEnameFromView = DeleteCategoryPageObj.getCatEnNameFromViewWithEvents(CategoryDMs);
        Log.info("category eng name to be clicked"+ CatEnameFromView);
        actualResults = DeleteCategoryPageObj.ClickDeleteButtonToCategoryWithEvents();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Delete Category Icon");
        actualResults = DeleteCategoryPageObj.YesConfirmationDeleteMessage();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Yes on Confirmation Delete Category");

        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        actualResults = updateCategoryPageObj.getAllErrMsgsForCategory(GeneralConstants.ERR_TYPE_NOTIFICATION,GeneralConstants.TESTING_IMAGE);
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ GeneralConstants.EXPECTED_CATEGORY_CANNOT_DELETED_ERROR);
        Assert.assertEquals(actualResults, GeneralConstants.EXPECTED_CATEGORY_CANNOT_DELETED_ERROR, GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");

        if (actualResults.contains(GeneralConstants.ERROR))
        {
            assertCategoryNotDeleted(CatEnameFromView);
        }




    }

    @Test(description = "delete category| verify that category has events couldn't be delete",priority = 2,enabled = true)
    public void VerifySearchByDeletedCategory()
    {
        test = extent.createTest("36393 --- delete category| verify that search by deleted category");
        Log.test = test;
        Log.startTestCase("36393 --- delete category| verify that search by deleted category");
        ViewCategoryPage DeleteCategoryPageObj = new ViewCategoryPage(driver);
        String NewAnynoumousName = DeleteCategoryPageObj.CreateAnynomousCatName();
        actualResults =DeleteCategoryPageObj.setCategoryName(NewAnynoumousName);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Delete Category Icon");
        actualResults = DeleteCategoryPageObj.CheckNoResultFoundDisplayed();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Delete Category Icon");

    }

    public void assertDeleteCategory(String CategoryEnNameFromViewPage,CategoryDM frontendModel)
    {
        //Backend verification. Assert that all data inserted in screen are the same inserted to corresponding DB columns
        SoftAssert softAssert = new SoftAssert();

        CategoryDM backendModel = backendService.getCategoryStatus(CategoryEnNameFromViewPage);

        // if program was found in DB, Use soft assert to validate all data and identify all failures if exist
        if(backendModel != null) {
            softAssert.assertEquals(backendModel.getCategory_Status_Code(), frontendModel.getCategory_Status_Code(),
                    CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.CATEGORY_STATUS_CODE + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertAll();
            Log.info(" *********  Backend Assertion passed successfully ********");
        }
        else {
            Log.info(" *********  Backend Assertion Failed as Category English_Name : " + frontendModel.getEnCategoryName() + " was not found in DB ********");
            Assert.fail("DB error occurred or Category was not found in DB");
        }

    }

    public void assertCategoryNotDeleted(String CategoryEnNameFromViewPage)
    {
        //Backend verification. Assert that all data inserted in screen are the same inserted to corresponding DB columns
        SoftAssert softAssert = new SoftAssert();

        CategoryDM backendModel = backendService.getCategoryStatus(CategoryEnNameFromViewPage);

        // if program was found in DB, Use soft assert to validate all data and identify all failures if exist
        if(backendModel != null) {
            softAssert.assertEquals(backendModel.getCategory_Status_Code(), GeneralConstants.EXPECTED_ACTIVE_CATEGORY_STATUS,
                    CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.CATEGORY_STATUS_CODE + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertAll();
            Log.info(" *********  Backend Assertion passed successfully ********");
        }
        else {
            Log.info(" *********  Backend Assertion Failed as Category English_Name : " + CategoryEnNameFromViewPage + " was not found in DB ********");
            Assert.fail("DB error occurred or Category was not found in DB");
        }

    }
    @DataProvider(name = "deleteCategoryDP")
    public Object[][] provideUpdateCategoryTD() {
        Object[][] updateCategoryDP = new Object[DeleteCategoryTestData.size()][1];
        for (int i = 0; i < DeleteCategoryTestData.size(); i++)
            updateCategoryDP[i][0] = DeleteCategoryTestData.get(i);

        return updateCategoryDP;
    }

    @BeforeClass
    public void delteCategoryTD() {

        ArrayList<ArrayList<Object>> resultArray = provideTestData("DeleteCategoryTD");

        for (ArrayList<Object> objects : resultArray) {
            CategoryDM CatDMObj = new CategoryDM();
            CatDMObj.setTestCaseId(objects.get(deleteCategoryExcelIndices.TEST_CASE_ID_INDEX).toString());
            CatDMObj.setTestCaseTitle(objects.get(deleteCategoryExcelIndices.TEST_CASE_TITLE_INDEX).toString());
            CatDMObj.setTestScope(objects.get(deleteCategoryExcelIndices.TEST_SCOPE_INDEX).toString());
            CatDMObj.setCategory_Status_Code(objects.get(deleteCategoryExcelIndices.CATEGORY_STATUS_CODE).toString());
            CatDMObj.setErrType(objects.get(deleteCategoryExcelIndices.ERROR_TYPE_INDEX).toString());
            CatDMObj.setExpectedMessage(objects.get(deleteCategoryExcelIndices.EXPECTED_MASSAGE_INDEX).toString());
            DeleteCategoryTestData.add(CatDMObj);

        }

    }
}
