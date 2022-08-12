package com.fawry.TicketsMall.angularAutomation.pages;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddAndUpdateCategoryPage extends MainPage{
    public AddAndUpdateCategoryPage(WebDriver driver) {
        super(driver);
    }

    String timeStamp = new SimpleDateFormat("hhmmss").format(new Date());


    @FindBy(xpath = "//input[@placeholder = 'English Category Name']")
    WebElement EnglishCategoryName;

    @FindBy(xpath = "//input[@placeholder = 'Arabic Category Name']")
    WebElement ArabicCategoryName;

    @FindBy(xpath = "//h5[contains(text(),'Add Category Image')]")
    WebElement AddCategoryimage;

    @FindBy(xpath = "//*[@type='file']")
    WebElement ImagePathElement;

    @FindBy(xpath ="//button[@pripple class='ng-tns-c44-12 p-dialog-header-icon p-dialog-header-close p-link p-ripple ng-star-inserted']")
    WebElement CloseSign;

    @FindBy(xpath = "//*[contains(@class,'alert alert-danger')]")
    public List<WebElement> CategoryMatErrMsgs;

    @FindBy(xpath = "//p[@class='p-error ng-star-inserted']")
    public WebElement CategoryImageMatErrMsgs;


    @FindBy(xpath ="//img[@class = 'img-max ng-star-inserted']")
    public WebElement ImageContent;

    @FindBy(xpath ="//button[@class = 'ml-4 blank Mmenu bgTransparent p-button p-component ng-star-inserted']")
    public WebElement OrdersMenu;

    @FindBy(xpath ="//img[@alt= 'Events']")
    public WebElement EventsPageLink;

    @FindBy(xpath ="//span[contains(text(),'Save')]")
    WebElement SaveButton;


    public String getAllErrMsgsForCategory(String errType,String image) {
        Log.info("Start collecting all massages");

        StringBuilder allErrorMsgsString = new StringBuilder();
        try {
            if (errType.equalsIgnoreCase(GeneralConstants.ERR_TYPE_PAGE)) {
                //wait just 3 seconds for all errors to be displayed

                Log.info("Number of displayed mat error messages in page " + CategoryMatErrMsgs.size());

                for (int i = 0; i < CategoryMatErrMsgs.size(); i++) {
                    if (CategoryMatErrMsgs.get(i).getText().isEmpty())
                        continue;
                    allErrorMsgsString.append(CategoryMatErrMsgs.get(i).getText());
                    if (i != CategoryMatErrMsgs.size() - 1)
                        allErrorMsgsString.append(GeneralConstants.STRING_DELIMITER);
                }
                if(image==""){
                    allErrorMsgsString.append(GeneralConstants.STRING_DELIMITER);
                    allErrorMsgsString.append(CategoryImageMatErrMsgs.getText());

                }

            } else if (errType.equalsIgnoreCase(GeneralConstants.ERR_TYPE_NOTIFICATION)) {
                Thread.sleep(500);
                Log.info("Number of displayed pop-up notification error messages in page is :  " + notificationMsgs.size());
                for (int j = 0; j < notificationMsgs.size(); j++) {
                    if (notificationMsgs.get(j).getText().isEmpty())
                        continue;
                    allErrorMsgsString.append(notificationMsgs.get(j).getText());
                    if (j != notificationMsgs.size() - 1)
                        allErrorMsgsString.append(GeneralConstants.STRING_DELIMITER);
                }
            }
            Log.info("Actual Result = " + allErrorMsgsString);
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
        return allErrorMsgsString.toString();
    }

    public String categoryImageUploaded(CategoryDM CategoryDMObj){
        try {
        if(CategoryDMObj.getCategoryimage()!=""){
            AddCategoryimage.click();
            ImagePathElement.sendKeys(System.getProperty("user.dir") + generalCofigsProps.getProperty(GeneralConstants.DEFAULT_UPLOAD_PATH) + "/" + CategoryDMObj.getCategoryimage());
            Log.info("Image Uploaded Successfully.");
            if(ImageContent.isDisplayed()||ImageContent.isEnabled()){
                Log.info("Image appears Successfully.");
            }
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


    public String addSameCategory(CategoryDM CategoryDMObj){
        try {
            if(CategoryDMObj.getTestCaseTitle().contains("same name")){
                setCategoryData(CategoryDMObj);
                Log.info("Image Uploaded Successfully.");
                if(ImageContent.isDisplayed()){
                    Log.info("Image appears Successfully.");
                }
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
    public String setCategoryData(CategoryDM CategoryDMObj) {
        try {
            Log.info("Set Category Data.");
         setTextValue(EnglishCategoryName , CategoryDMObj.getEnCategoryName());
          setTextValue(ArabicCategoryName , CategoryDMObj.getArCategoryName());
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


    public void appendTimestamp(CategoryDM CategoryDMObj)
    {
        if(CategoryDMObj.getEnCategoryName() != "")
            CategoryDMObj.setEnCategoryName(CategoryDMObj.getEnCategoryName()+timeStamp);
        if(CategoryDMObj.getArCategoryName() != "")
            CategoryDMObj.setArCategoryName(CategoryDMObj.getArCategoryName()+timeStamp);
     // return CategoryDMObj;
    }

    public String clickSaveButton() {
        try {
            Log.info("Click save button.");
            scrollIntoViewAndClick(SaveButton);

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
