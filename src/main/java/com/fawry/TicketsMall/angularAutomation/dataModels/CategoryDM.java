package com.fawry.TicketsMall.angularAutomation.dataModels;

public class CategoryDM extends MainDataModel{

    private String EnCategoryName;
    private String ArCategoryName;
    private String Categoryimage;
    private String CategoryCode;

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
