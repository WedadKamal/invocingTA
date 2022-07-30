package com.fawry.TicketsMall.angularAutomation.pages;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.VenueDM;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddVenuePage extends MainPage{

    public AddVenuePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[contains(text(),'Description In Arabic')]")
    WebElement descriptionInArabicButton;

    @FindBy(name = "venueNameEn")
    WebElement venueNameEnTextInput;

    @FindBy(id = "descriptionEn")
    WebElement descriptionEnTextInput;

    @FindBy(name = "venueNameAr")
    WebElement venueNameArTextInput;

    @FindBy(id = "descriptionAr")
    WebElement descriptionArTextInput;

    @FindBy(name = "locationDesc")
    WebElement locationDescTextInput;

    @FindBy(xpath = "//p-togglebutton//span[contains(@class,'p-button-icon')]")
    WebElement unseatedVenueButton;

    @FindBy(name = "seatMap")
    WebElement seatMapTextInput;

    @FindBy(name = "catName")
    WebElement catNameTextInput;

    @FindBy(name = "numOfTickits")
    WebElement numOfTicketsTextInput;

    @FindBy(xpath = "//p-button//button")
    WebElement addCategoryButton;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    WebElement saveButton;

    String timeStamp = new SimpleDateFormat("hhmmss").format(new Date());

    public String setVenueDetails(VenueDM venueDM) {
        try {
            Log.info("Set Venue Details.");
            appendTimestamp(venueDM);
            setTextValue(venueNameEnTextInput , venueDM.getNameEn());
            setTextValue(descriptionEnTextInput , venueDM.getDescriptionEn());
            descriptionInArabicButton.click();
            setTextValue(venueNameArTextInput , venueDM.getNameAr());
            setTextValue(descriptionArTextInput , venueDM.getDescriptionAr());
            setTextValue(locationDescTextInput , venueDM.getLocationDesc());
            if(venueDM.getSeatedFlag().contains(GeneralConstants.SEATED))
            {
                scrollIntoView(unseatedVenueButton);
                unseatedVenueButton.click();
                FileInputStream seatMap = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\testDataExcelFiles\\Seat_Map.txt");
                venueDM.setSeatMap(IOUtils.toString(seatMap, "UTF-8"));
                jsDriver.executeScript("arguments[1].value=arguments[0]"
                        , "'" + venueDM.getSeatMap() + "'"
                        , seatMapTextInput);
                seatMapTextInput.sendKeys(Keys.SPACE);

            }

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


    public String addCategory(VenueDM venueDM) {
        try {
            Log.info("Add Seat Category.");
            scrollIntoView(catNameTextInput);
            setTextValue(catNameTextInput , venueDM.getCatName());
            setTextValue(numOfTicketsTextInput , venueDM.getNumOfTickets());
            addCategoryButton.click();

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

    public String clickSaveButton() {
        try {
            Log.info("Click save button.");
            scrollIntoView(saveButton);
           saveButton.click();

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

    public VenueDM appendTimestamp(VenueDM venueDM)
    {
        if(venueDM.getNameEn() != "")
            venueDM.setNameEn(venueDM.getNameEn()+timeStamp);
        if(venueDM.getNameAr() != "")
            venueDM.setNameAr(venueDM.getNameAr()+timeStamp);
        if(venueDM.getDescriptionAr() != "")
            venueDM.setDescriptionAr(venueDM.getDescriptionAr()+timeStamp);
        if(venueDM.getDescriptionEn() != "")
            venueDM.setDescriptionEn(venueDM.getDescriptionEn()+timeStamp);
        return venueDM;
    }
}
