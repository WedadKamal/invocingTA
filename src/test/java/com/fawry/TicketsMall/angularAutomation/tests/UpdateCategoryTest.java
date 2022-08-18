package com.fawry.TicketsMall.angularAutomation.tests;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.constants.excelIndices.addCategoryExcelIndices;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddAndUpdateCategoryPage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class UpdateCategoryTest extends BaseTest{

    ArrayList UpdateCategoryTestData = new ArrayList();

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
        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        actualResults = updateCategoryPageObj.ClickEditButton();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking Edit Category Icon");
    }

    @Test(description = "Validate Update Category functionalities",priority = 0  ,dataProvider = "updateCategoryDP" ,enabled = true)
    public void updateCategory(CategoryDM CatDMObj) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());
        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);

            actualResults = updateCategoryPageObj.UpdateImageCategory(CatDMObj);
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While upload Category image");
            updateCategoryPageObj.appendTimestamp(CatDMObj);
            actualResults = updateCategoryPageObj.setCategoryData(CatDMObj);
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While setting Category data");
            actualResults = updateCategoryPageObj.clickSaveButton();
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
            actualResults = updateCategoryPageObj.getAllErrMsgsForCategory(CatDMObj.getErrType(),CatDMObj.getCategoryimage());
            Log.info(" *********  Start frontend Assertion  ********");
            Log.info("Expected massage = "+ CatDMObj.getExpectedMessage());
            Assert.assertEquals(actualResults, CatDMObj.getExpectedMessage(), GeneralConstants.Expected_ERR_MSG );
            Log.info(" *********  Frontend Assertion passed successfully ********");
            if (!CatDMObj.getErrType().contains(GeneralConstants.ERR_TYPE_PAGE)&&CatDMObj.getExpectedMessage().contains(GeneralConstants.SUCCESS))
            {
                assertUpdateCategory(CatDMObj);
            }

    }

    @Test(description = "edit category| verify that edit with exist name in another category",priority = 1 ,enabled = true)
    public void updateWithExistName() {
        //Create extent test to be logged in report using test case title
        test = extent.createTest("34488 --- edit category| verify that edit with exist name in another category");
        Log.test = test;
        Log.startTestCase("34488 --- edit category| verify that edit with exist name in another category");
        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        CategoryDM DMObjDB=  getFirstCategoryFromDB();
        actualResults = updateCategoryPageObj.UpdateImageCategoryStatic(GeneralConstants.TESTING_IMAGE);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While upload Category image");
        actualResults = updateCategoryPageObj.setCategoryData(DMObjDB);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While setting Category data");
        actualResults = updateCategoryPageObj.clickSaveButton();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
        actualResults = updateCategoryPageObj.getAllErrMsgsForCategory(GeneralConstants.ERR_TYPE_NOTIFICATION,GeneralConstants.TESTING_IMAGE);
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ GeneralConstants.EXPECTED_SAME_NAME_ERROR);
        Assert.assertEquals(actualResults, GeneralConstants.EXPECTED_SAME_NAME_ERROR, GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");

    }

    @Test(description = "edit category| verify that the category not updated when press save without make any changes",priority =2,enabled = true)
    public void updateCategoryWithoutMakeAnyChange(){
        //Create extent test to be logged in report using test case title
        test = extent.createTest("34487 --- edit category| verify that the category not updated when press save without make any changes");
        Log.test = test;
        Log.startTestCase("34487 --- edit category| verify that the category not updated when press save without make any changes");
        AddAndUpdateCategoryPage updateCategoryPageObj = new AddAndUpdateCategoryPage(driver);
        CategoryDM CatDMFromPage = updateCategoryPageObj.getCatDataFromUpdatePage();
        actualResults = updateCategoryPageObj.clickSaveButton();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
        actualResults = updateCategoryPageObj.getAllErrMsgsForCategory(GeneralConstants.ERR_TYPE_NOTIFICATION,GeneralConstants.TESTING_IMAGE);
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ GeneralConstants.EXPECTED_UPDATED_MESSAGE);
        Assert.assertEquals(actualResults, GeneralConstants.EXPECTED_UPDATED_MESSAGE, GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");

        if (actualResults.contains(GeneralConstants.SUCCESS))
        {
            assertUpdateCategory(CatDMFromPage);
        }


    }
    public void assertUpdateCategory(CategoryDM frontendModel)
    {
        //Backend verification. Assert that all data inserted in screen are the same inserted to corresponding DB columns
        SoftAssert softAssert = new SoftAssert();

        CategoryDM backendModel = backendService.getCategoryDetails(frontendModel.getEnCategoryName());

        // if program was found in DB, Use soft assert to validate all data and identify all failures if exist
        if(backendModel != null) {
            softAssert.assertEquals(backendModel.getEnCategoryName(), frontendModel.getEnCategoryName(),
                    CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.NAME_PRIMARY_LANG + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getArCategoryName(), frontendModel.getArCategoryName(),
                    CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.NAME_SECONDARY_LANG + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertTrue(!backendModel.getCategoryimage().equals(null) && !backendModel.getCategoryimage().equals(""), CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.CATEGORY_IMAGE_URL + GeneralConstants.MISMATCH_ERR_MSG);
/*            softAssert.assertEquals(backendModel.getCategoryimage(), frontendModel.getCategoryimage(),
                    CategoryDBTable.TABLE_NAME + "." + CategoryDBTable.CATEGORY_IMAGE_URL + GeneralConstants.MISMATCH_ERR_MSG);*/
            softAssert.assertAll();
            Log.info(" *********  Backend Assertion passed successfully ********");
        }
        else {
            Log.info(" *********  Backend Assertion Failed as Category English_Name : " + frontendModel.getEnCategoryName() + " was not found in DB ********");
            Assert.fail("DB error occurred or Category was not found in DB");
        }

    }

    public CategoryDM getFirstCategoryFromDB()
    {
        CategoryDM backendModel = backendService.getAllCategoryDetails();
        return backendModel;
    }
    @DataProvider(name = "updateCategoryDP")
    public Object[][] provideUpdateCategoryTD() {
        Object[][] updateCategoryDP = new Object[UpdateCategoryTestData.size()][1];
        for (int i = 0; i < UpdateCategoryTestData.size(); i++)
            updateCategoryDP[i][0] = UpdateCategoryTestData.get(i);

        return updateCategoryDP;
    }

    @BeforeClass
    public void updateCategoryTD() {

        ArrayList<ArrayList<Object>> resultArray = provideTestData("UpdateCategoryTD");

        for (ArrayList<Object> objects : resultArray) {
            CategoryDM CatDMObj = new CategoryDM();
            CatDMObj.setTestCaseId(objects.get(addCategoryExcelIndices.TEST_CASE_ID_INDEX).toString());
            CatDMObj.setTestCaseTitle(objects.get(addCategoryExcelIndices.TEST_CASE_TITLE_INDEX).toString());
            CatDMObj.setTestScope(objects.get(addCategoryExcelIndices.TEST_SCOPE_INDEX).toString());
            CatDMObj.setEnCategoryName(objects.get(addCategoryExcelIndices.CATEGORY_ENGLISH_NAME).toString());
            CatDMObj.setArCategoryName(objects.get(addCategoryExcelIndices.CATEGORY_ARABIC_NAME).toString());
            CatDMObj.setCategoryimage(objects.get(addCategoryExcelIndices.CATEGORY_IMAGE).toString());
            CatDMObj.setErrType(objects.get(addCategoryExcelIndices.ERROR_TYPE_INDEX).toString());
            CatDMObj.setExpectedMessage(objects.get(addCategoryExcelIndices.EXPECTED_MASSAGE_INDEX).toString());
            CatDMObj.setSecondExpectedMessage(objects.get(addCategoryExcelIndices.SECOND_EXPECTED_MASSAGE_INDEX).toString());
            UpdateCategoryTestData.add(CatDMObj);

        }

    }

}
