package com.fawry.TicketsMall.angularAutomation.tests;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.constants.database.VenueDBTable;
import com.fawry.TicketsMall.angularAutomation.constants.excelIndices.AddVenueExcelIndices;
import com.fawry.TicketsMall.angularAutomation.dataModels.VenueDM;
import com.fawry.TicketsMall.angularAutomation.pages.AddVenuePage;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class VenueTest extends BaseTest{

    ArrayList addVenueTestData = new ArrayList();



    @BeforeMethod(alwaysRun = true)
    public void navigateToAddVenuePage() {
        Assert.assertEquals(homepage.getWelcomeMsg(), "Welcome", "BLOCKING ISSUE - CAN NOT LOGIN TO APPLICATION");
        String navigatedToPageSuccessfully = homepage.navigateToAddVenuePage();
        Assert.assertEquals(navigatedToPageSuccessfully, GeneralConstants.SUCCESS, "Could not navigate to Add Venue page successfully");
    }

    @Test(description = "Validate add venue functionalities",priority = 0  ,dataProvider = "addVenueDP" ,enabled = true)
    public void addVenue(VenueDM venueDM) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(venueDM.getTestCaseId() + " --- " + venueDM.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(venueDM.getTestCaseId() + " --- " + venueDM.getTestCaseTitle());
        AddVenuePage addVenuePage = new AddVenuePage(driver);
        String actualResults = addVenuePage.setVenueDetails(venueDM);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While setting venue data");
        actualResults = addVenuePage.addCategory(venueDM);
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While adding category");
        actualResults = addVenuePage.clickSaveButton();
        Assert.assertEquals(actualResults, GeneralConstants.SUCCESS, GeneralConstants.POM_EXCEPTION_ERR_MSG + " While clicking save button.");
        actualResults = addVenuePage.getAllErrMsgs(venueDM.getErrType());
        Log.info(" *********  Start frontend Assertion  ********");
        Log.info("Expected massage = "+ venueDM.getExpectedMessage());
        Assert.assertEquals(actualResults, venueDM.getExpectedMessage(), GeneralConstants.Expected_ERR_MSG );
        Log.info(" *********  Frontend Assertion passed successfully ********");
        if (!venueDM.getErrType().contains(GeneralConstants.ERR_TYPE_PAGE))
        {
            assertAddVenue(venueDM);
        }
        driver.navigate().refresh();
    }

    public void assertAddVenue(VenueDM frontendModel)
    {
        //Backend verification. Assert that all data inserted in screen are the same inserted to corresponding DB columns
        SoftAssert softAssert = new SoftAssert();
        VenueDM backendModel = backendService.getVenueDetails(frontendModel.getNameEn());

        // if program was found in DB, Use soft assert to validate all data and identify all failures if exist
        if(backendModel != null) {
            softAssert.assertEquals(backendModel.getNameEn(), frontendModel.getNameEn(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.NAME_EN + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getDescriptionEn(), frontendModel.getDescriptionEn(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.DESCRIPTION_EN + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getNameAr(), frontendModel.getNameAr(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.NAME_AR + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getDescriptionAr(), frontendModel.getDescriptionAr(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.DESCRIPTION_AR + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getLocationDesc(), frontendModel.getLocationDesc(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.LOCATION + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getSeatedFlag(), frontendModel.getSeatedFlag(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.SEATED_FLAG + GeneralConstants.MISMATCH_ERR_MSG);
           if(frontendModel.getSeatedFlag().contains(GeneralConstants.SEATED)) {
               softAssert.assertEquals(backendModel.getSeatMap().trim()
                               .substring(1, backendModel.getSeatMap().length() - 2)
                               .replaceAll("\n", "").replaceAll("\r", "")
                       , frontendModel.getSeatMap().trim()
                               .replaceAll("\n", "").replaceAll("\r", ""),
                       VenueDBTable.TABLE_NAME + "." + VenueDBTable.SEAT_MAP + GeneralConstants.MISMATCH_ERR_MSG);
           }
            softAssert.assertEquals(backendModel.getCatName(), frontendModel.getCatName(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.CATEGORY_NAME + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertEquals(backendModel.getNumOfTickets(), frontendModel.getNumOfTickets(),
                    VenueDBTable.TABLE_NAME + "." + VenueDBTable.NUMBER_OF_SEATS + GeneralConstants.MISMATCH_ERR_MSG);
            softAssert.assertAll();
            Log.info(" *********  Backend Assertion passed successfully ********");
        }
        else {
            Log.info(" *********  Backend Assertion Failed as Venue English_Name : " + frontendModel.getNameEn() + " was not found in DB ********");
            Assert.fail("DB error occurred or Venue was not found in DB");
        }

    }


    @DataProvider(name = "addVenueDP")
    public Object[][] provideAddBusinessEntityTD() {
        Object[][] addVenueDP = new Object[addVenueTestData.size()][1];
        for (int i = 0; i < addVenueTestData.size(); i++)
            addVenueDP[i][0] = addVenueTestData.get(i);

        return addVenueDP;
    }

    @BeforeClass
    public void AddVenueTD() {

        ArrayList<ArrayList<Object>> resultArray = provideTestData("addVenue");

        for (ArrayList<Object> objects : resultArray) {
            VenueDM venueDM = new VenueDM();
            venueDM.setTestCaseId(objects.get(AddVenueExcelIndices.TEST_CASE_ID_INDEX).toString());
            venueDM.setTestCaseTitle(objects.get(AddVenueExcelIndices.TEST_CASE_TITLE_INDEX).toString());
            venueDM.setTestScope(objects.get(AddVenueExcelIndices.TEST_SCOPE_INDEX).toString());
            venueDM.setNameEn(objects.get(AddVenueExcelIndices.NAME_EN_INDEX).toString());
            venueDM.setDescriptionEn(objects.get(AddVenueExcelIndices.DESCRIPTION_EN_INDEX).toString());
            venueDM.setNameAr(objects.get(AddVenueExcelIndices.NAME_AR_INDEX).toString());
            venueDM.setDescriptionAr(objects.get(AddVenueExcelIndices.DESCRIPTION_AR_INDEX).toString());
            venueDM.setLocationDesc(objects.get(AddVenueExcelIndices.LOCATION_INDEX).toString());
            venueDM.setSeatedFlag(objects.get(AddVenueExcelIndices.SEATED_FLAG_INDEX).toString());
            venueDM.setSeatMap(objects.get(AddVenueExcelIndices.SEAT_MAP_INDEX).toString());
            venueDM.setCatName(objects.get(AddVenueExcelIndices.CATEGORY_NAME_INDEX).toString());
            venueDM.setNumOfTickets(objects.get(AddVenueExcelIndices.NUMBER_OF_SEATS_INDEX).toString());
            venueDM.setErrType(objects.get(AddVenueExcelIndices.ERROR_TYPE_INDEX).toString());
            venueDM.setExpectedMessage(objects.get(AddVenueExcelIndices.EXPECTED_MASSAGE_INDEX).toString());


            addVenueTestData.add(venueDM);

        }

    }
}
