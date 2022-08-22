package com.fawry.TicketsMall.angularAutomation.backendServices.database.daos;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable.TABLE_NAME_LOCKUP;

public class CategoryDAO {

    public CategoryDM getCategoryDetails(Connection dbConn, String CategoryEnName) throws SQLException {


        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append(        "select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                        " MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL, MERCHANT_ITEM_CATEGORY.CATEGORY_CODE,\n" +
                        " MERCHANT_ITEM_CATEGORY.STATUS_ID from MERCHANT_ITEM_CATEGORY where MERCHANT_ID = 4606 AND MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG = '"+CategoryEnName+"'");
        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet CategoryRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        CategoryDM CategoryDM = null;

        if (CategoryRS.next())
        {
            CategoryDM = new CategoryDM();
            CategoryDM.setEnCategoryName(CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG));
            CategoryDM.setArCategoryName(CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG));
            CategoryDM.setCategoryimage(CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL));
            CategoryDM.setCategoryCode(CategoryRS.getString(CategoryDBTable.CATEGORY_CODE)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_CODE));
            CategoryDM.setCategory_Status_ID(CategoryRS.getString(CategoryDBTable.STATUS_ID)==null?"":CategoryRS.getString(CategoryDBTable.STATUS_ID));

        }
        return CategoryDM;
    }
    public CategoryDM getAllCategoryDetails(Connection dbConn) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append("      select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                "  MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL from MERCHANT_ITEM_CATEGORY where MERCHANT_ITEM_CATEGORY.MERCHANT_ID = 4606 AND MERCHANT_ITEM_CATEGORY.STATUS_ID=1");

        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet CategoryRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        CategoryDM CategoryDM = null;
        List<String> CatEnName = new ArrayList<String>();
        List<String> CatArName = new ArrayList<String>();
        List<String> Image = new ArrayList<String>();

        while (CategoryRS.next())
        {
/*            CategoryDM = new CategoryDM();
            CategoryDM.setEnCategoryName(CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG));
            CategoryDM.setArCategoryName(CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG));
            CategoryDM.setCategoryimage(CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL));*/

            CatEnName.add(CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG));
            CatArName.add(CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG));
            Image.add(CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL));
        }
        CategoryDM = new CategoryDM();
        CategoryDM.setEnCategoryName(CatEnName.get(0));
        CategoryDM.setArCategoryName(CatEnName.get(0));
        CategoryDM.setCategoryimage(CatEnName.get(0));

        return CategoryDM;
    }
    public CategoryDM getCategoryStatus(Connection dbConn, String CategoryEnName) throws SQLException {


        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append(
        "select LOOKUP_TYPES_STATUS.CODE from LOOKUP_TYPES_STATUS where\n" +
                "LOOKUP_TYPES_STATUS.ID = (select MERCHANT_ITEM_CATEGORY.STATUS_ID from MERCHANT_ITEM_CATEGORY where \n" +
                "MERCHANT_ITEM_CATEGORY.MERCHANT_ID = (select ECOMMERCE_MERCHANTS.ID from ECOMMERCE_MERCHANTS where ECOMMERCE_MERCHANTS.EMAIL='TicketsVendor@fawry.com'\n" +
                "AND MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG =  '"+CategoryEnName+"'"+"))");
        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet CategoryRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        CategoryDM CategoryDM = null;

        if (CategoryRS.next())
        {
            CategoryDM = new CategoryDM();
            CategoryDM.setCategory_Status_Code(CategoryRS.getString(CategoryDBTable.CATEGORY_STATUS_CODE)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_STATUS_CODE));

        }
        return CategoryDM;
    }
    public CategoryDM getDeletedCategoryDetails(Connection dbConn) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append(" select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                "                MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL from MERCHANT_ITEM_CATEGORY where MERCHANT_ITEM_CATEGORY.MERCHANT_ID = 4606 AND MERCHANT_ITEM_CATEGORY.STATUS_ID=3");

        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet CategoryRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        CategoryDM CategoryDM = null;
        List<String> CatEnName = new ArrayList<String>();
        List<String> CatArName = new ArrayList<String>();
        List<String> Image = new ArrayList<String>();

        while (CategoryRS.next())
        {
            CatEnName.add(CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_PRIMARY_LANG));
            CatArName.add(CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG)==null?"":CategoryRS.getString(CategoryDBTable.NAME_SECONDARY_LANG));
            Image.add(CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL)==null?"":CategoryRS.getString(CategoryDBTable.CATEGORY_IMAGE_URL));
        }
        CategoryDM = new CategoryDM();
        CategoryDM.setEnCategoryName(CatEnName.get(0));
        CategoryDM.setArCategoryName(CatEnName.get(0));
        CategoryDM.setCategoryimage(CatEnName.get(0));

        return CategoryDM;
    }
    public CategoryDM getCategoryEventsCount(Connection dbConn,String CategoryEnName) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append(" select * from MERCHANT_ITEMS where MERCHANT_ITEM_CATEGORY_ID = \n" +
                "(select MERCHANT_ITEM_CATEGORY.ID FROM MERCHANT_ITEM_CATEGORY WHERE NAME_PRIMARY_LANG =  '"+CategoryEnName+"'"+")" +
                "AND MERCHANT_ITEMS.STATUS_ID = 1");

        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet CategoryRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        CategoryDM CategoryDM = null;
        List<String> CatEnName = new ArrayList<String>();
        List<String> CatArName = new ArrayList<String>();
        List<String> Image = new ArrayList<String>();

        while (CategoryRS.next())
        {
            CategoryDM = new CategoryDM();
            CategoryDM.setCategoryEventsCount(CategoryRS.getRow());
        }

        return CategoryDM;
    }

}
