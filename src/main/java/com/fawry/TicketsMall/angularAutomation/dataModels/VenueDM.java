package com.fawry.TicketsMall.angularAutomation.dataModels;

public class VenueDM extends  MainDataModel{

    private String nameEn;
    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    private String locationDesc;
    private String seatedFlag;
    private String seatMap;
    private String  catName;
    private String  numOfTickets;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getSeatedFlag() {
        return seatedFlag;
    }

    public void setSeatedFlag(String seatedFlag) {
        this.seatedFlag = seatedFlag;
    }

    public String getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(String seatMap) {
        this.seatMap = seatMap;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(String numOfTickets) {
        this.numOfTickets = numOfTickets;
    }
}
