package com.fawry.TicketsMall.angularAutomation.pages;

import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;
import com.fawry.TicketsMall.angularAutomation.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewCategoryPage extends MainPage{
    public ViewCategoryPage(WebDriver driver) {
        super(driver);
    }

    String timeStamp = new SimpleDateFormat("hhmmss").format(new Date());

@FindBy(xpath = "//button[@class='p-button-outlined p-button-rounded p-button-danger mr-3 p-button p-component p-button-icon-only']")
    List<WebElement> DeleteButton;

    @FindBy(xpath = "//button[@class='ng-tns-c59-1 p-confirm-popup-accept p-button-sm p-button p-component ng-star-inserted']")
    WebElement YesConfirmationButton;
    @FindBy(xpath = "//input[@type='text']")
    WebElement SearchText;

    @FindBy(xpath = "//span[contains(text(),'Are you sure that you want to proceed?')]")
    WebElement ConfirmationMenu;
    @FindBy(xpath = "//tbody//tr")
    List<WebElement> searchResultsRows;
    @FindBy(xpath = "//div[contains(text(),' No results found ')]")
    WebElement NoResultFound;

    public String actual;
    public int CategoryWithoutEventsIndex = getRowIndexForCategoryRowWithoutEvents(getViewCategoryData());
    public int CategoryWithEventsIndex = getRowIndexForCategoryRowWithEvents(getViewCategoryData());
    public String ClickDeleteButtonToCategoryWithoutEvents() {
        try {
            Log.info("Click Category Delete button.");
       //   scrollIntoViewAndClick(DeleteButton);
          DeleteButton.get(CategoryWithoutEventsIndex).click();
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
    public String ClickDeleteButtonToCategoryWithEvents() {
        try {
            Log.info("Click Category Delete button.");
            //   scrollIntoViewAndClick(DeleteButton);
            DeleteButton.get(CategoryWithEventsIndex).click();
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

    public String CheckNoResultFoundDisplayed() {
        String NoResultFoundMsg = null;
        try {
            if(NoResultFound.isDisplayed()||NoResultFound.isEnabled()){
                NoResultFoundMsg= NoResultFound.getText();
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
        return NoResultFoundMsg;
    }
    public String setSearchCategoryName(String AnynoumousName){
        try {
            Log.info("Search with Category Name "+ AnynoumousName);
            setTextValue(SearchText , AnynoumousName);
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
    public String searchBusinessEntityByName(String name) {

        String mail;
        try {
            setTextValue(SearchText, name);
            Thread.sleep(1000);
            List<WebElement> rows = searchResultsRows.get(0).findElements(By.tagName("td"));
            mail = rows.get(3).getText();
        }
        catch(Exception e)
        {
            Log.error("Error occurred in "+new Object(){}
                    .getClass().getName()+"."+new Object(){}
                    .getClass()
                    .getEnclosingMethod()
                    .getName(),e);
            return GeneralConstants.FAILED;
        }
        return mail;
    }
    public String CreateAnynomousCatName() {
        String AnynomousName;
        try {
            AnynomousName= "Cat" +timeStamp;
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
        return AnynomousName;
    }

    public String YesConfirmationDeleteMessage(){
        try {
            Log.info("Click Yes Confirmation Delete Message.");
            if(ConfirmationMenu.isDisplayed()||ConfirmationMenu.isEnabled()){
                Log.info("Confirmation Message Appears Successfully before Deleteing.");
            }
           // scrollIntoViewAndClick(YesConfirmationButton);
            YesConfirmationButton.click();

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

    public ArrayList<CategoryDM> getViewCategoryData() {
        ArrayList<CategoryDM> CategoryDMS = new ArrayList<>();
        CategoryDM CategoryDM;
        List<WebElement> rows = null;
        try {

                    for (WebElement searchResultsRow : searchResultsRows) {
                        CategoryDM = new CategoryDM();
                         rows = searchResultsRow.findElements(By.tagName("td"));
                        CategoryDM.setEnCategoryName(rows.get(1).getText());
                        CategoryDM.setCategory_Events(rows.get(3).getText());
                        CategoryDMS.add(CategoryDM);
                    }

        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            actual = GeneralConstants.FAILED;
            driver.navigate().refresh();
            return null;
        }
        actual = GeneralConstants.SUCCESS;
        return CategoryDMS;
    }
    public String getCatEnNameFromViewWithoutEvents (ArrayList<CategoryDM> CategoryDMS){
        String CatEnNameFromView;
        CatEnNameFromView = CategoryDMS.get(CategoryWithoutEventsIndex).getEnCategoryName();
        return CatEnNameFromView;
    }
    public String getCatEnNameFromViewWithEvents (ArrayList<CategoryDM> CategoryDMS){
        String CatEnNameFromView;
        CatEnNameFromView = CategoryDMS.get(CategoryWithEventsIndex).getEnCategoryName();
        return CatEnNameFromView;
    }
    public int getRowIndexForCategoryRowWithoutEvents (ArrayList<CategoryDM> CategoryDMS){

        String EventsZero ;
        int getIndexEvents = 0;
        String CatEnNameFromView;
        for (int i = 0 ; i< CategoryDMS.size(); i++){

            if (CategoryDMS.get(i).getCategory_Events().equalsIgnoreCase("Events (0)")){
                EventsZero=CategoryDMS.get(i).getCategory_Events();
                getIndexEvents=i;
                break;
            }
        }

      //  Log.info("index is " + getIndexEvents);
        return getIndexEvents;
    }
    public CategoryDM getCatDMFromViewTable (ArrayList<CategoryDM> CategoryDMS,String EnCatName){
        CategoryDM  CategoryDM = new CategoryDM();

        try {
        for (int i = 0 ; i< CategoryDMS.size(); i++){


            if (CategoryDMS.get(i).getEnCategoryName().equalsIgnoreCase(EnCatName)){
                CategoryDM.setEnCategoryName(CategoryDMS.get(i).getEnCategoryName());
                CategoryDM.setCategory_Events(CategoryDMS.get(i).getCategory_Events());
                break;
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
        actual = GeneralConstants.FAILED;
        driver.navigate().refresh();
        return null;
    }

        //  Log.info("index is " + getIndexEvents);
        return CategoryDM;
    }

    public int getRowIndexForCategoryRowWithEvents (ArrayList<CategoryDM> CategoryDMS){

        String EventsZero ;
        int getIndexEvents = 0;
        String CatEnNameFromView;
        for (int i = 0 ; i< CategoryDMS.size(); i++){

            if (!CategoryDMS.get(i).getCategory_Events().equalsIgnoreCase("Events (0)")){
                EventsZero=CategoryDMS.get(i).getCategory_Events();
                getIndexEvents=i;
                break;
            }
        }

       // Log.info("index is " + getIndexEvents);
        return getIndexEvents;
    }


}
