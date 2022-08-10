package com.fawry.TicketsMall.angularAutomation.tests;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.constants.excelIndices.addCategoryExcelIndices;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddCategoryPage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class CategoryTest extends BaseTest{

    @BeforeClass(alwaysRun = true)
    public void navigateToVenuePage(){
        String navigatedToPageSuccessfully = homepage.navigateToVenuePage();
        Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Venue Page successfully");
    }

    ArrayList addCategoryTestData = new ArrayList();
    @BeforeMethod(alwaysRun = true)
    public void navigateToAddCategoryPage() {
        Assert.assertEquals(homepage.getWelcomeMsg(), "Welcome", "BLOCKING ISSUE - CAN NOT LOGIN TO APPLICATION");
        driver.navigate().refresh();
        String navigatedToPageSuccessfully = homepage.navigateToAddCategoryPage();
        Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Add Category page successfully");
    }

    @Test(description = "Validate add Category functionalities",priority = 0  ,dataProvider = "addCategoryDP" ,enabled = true)
    public void addCategory(CategoryDM CatDMObj) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(CatDMObj.getTestCaseId() + " --- " + CatDMObj.getTestCaseTitle());

        AddCategoryPage addCategoryPageObj = new AddCategoryPage(driver);
        String actualResults = addCategoryPageObj.categoryImageUploaded(CatDMObj);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While upload Category image");
        addCategoryPageObj.appendTimestamp(CatDMObj);
        actualResults = addCategoryPageObj.setCategoryData(CatDMObj);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While setting Category data");
        actualResults = addCategoryPageObj.clickSaveButton();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
        actualResults = addCategoryPageObj.getAllErrMsgsForCategory(CatDMObj.getErrType(),CatDMObj.getCategoryimage());
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ CatDMObj.getExpectedMessage());
        Assert.assertEquals(actualResults, CatDMObj.getExpectedMessage(), GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");
        if (!CatDMObj.getErrType().contains(GeneralConstants.ERR_TYPE_PAGE)&&CatDMObj.getExpectedMessage().contains(GeneralConstants.SUCCESS))
        {
            assertaddCategory(CatDMObj);
        }
        if(CatDMObj.getTestCaseTitle().contains("same name")){
            String navigatedToPageSuccessfully = homepage.navigateToAddCategoryPage();
            Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Add Category page successfully");
            actualResults = addCategoryPageObj.setCategoryData(CatDMObj);
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While setting Category data");
            actualResults = addCategoryPageObj.categoryImageUploaded(CatDMObj);
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While upload Category image");
            actualResults = addCategoryPageObj.clickSaveButton();
            Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
            actualResults = addCategoryPageObj.getAllErrMsgsForCategory(CatDMObj.getErrType(),CatDMObj.getCategoryimage());
            Log.info(" *********  Start frontend Assertion  ********");
            Log.info("Expected massage = "+ CatDMObj.getSecondExpectedMessage());
            Assert.assertEquals(actualResults, CatDMObj.getSecondExpectedMessage(), GeneralConstants.Expected_ERR_MSG );
            Log.info(" *********  Frontend Assertion passed successfully ********");
        }

    }

  public void assertaddCategory(CategoryDM frontendModel)
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


    @DataProvider(name = "addCategoryDP")
    public Object[][] provideAddCategoryTD() {
        Object[][] addCategoryDP = new Object[addCategoryTestData.size()][1];
        for (int i = 0; i < addCategoryTestData.size(); i++)
            addCategoryDP[i][0] = addCategoryTestData.get(i);

        return addCategoryDP;
    }

    @BeforeClass
    public void addCategoryTD() {

        ArrayList<ArrayList<Object>> resultArray = provideTestData("CategoryTD");

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
            addCategoryTestData.add(CatDMObj);

        }

    }

}
