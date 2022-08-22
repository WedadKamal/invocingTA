package com.fawry.TicketsMall.angularAutomation.dataModels;

public class CategoryDM extends MainDataModel{

    private String EnCategoryName;
    private String ArCategoryName;
    private String Categoryimage;
    private String CategoryCode;
    private String Category_Status_ID;
    private String Category_Events;
    private String Category_Status_Code;
    private int CategoryEventsCount;


    public int getCategoryEventsCount() {
        return CategoryEventsCount;
    }

    public void setCategoryEventsCount(int categoryEventsCount) {
        CategoryEventsCount = categoryEventsCount;
    }

    public String getCategory_Status_Code() {
        return Category_Status_Code;
    }

    public void setCategory_Status_Code(String category_Status_Code) {
        Category_Status_Code = category_Status_Code;
    }

    public String getCategory_Events() {
        return Category_Events;
    }

    public void setCategory_Events(String category_Events) {
        Category_Events = category_Events;
    }



    public String getCategory_Status_ID() {
        return Category_Status_ID;
    }

    public void setCategory_Status_ID(String category_Status_ID) {
        Category_Status_ID = category_Status_ID;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getEnCategoryName() {
        return EnCategoryName;
    }

    public void setEnCategoryName(String enCategoryName) {
        EnCategoryName = enCategoryName;
    }

    public String getArCategoryName() {
        return ArCategoryName;
    }

    public void setArCategoryName(String arCategoryName) {
        ArCategoryName = arCategoryName;
    }

    public String getCategoryimage() {
        return Categoryimage;
    }

    public void setCategoryimage(String categoryimage) {
        Categoryimage = categoryimage;
    }



}
