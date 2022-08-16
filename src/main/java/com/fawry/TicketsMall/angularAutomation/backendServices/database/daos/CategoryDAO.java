package com.fawry.TicketsMall.angularAutomation.backendServices.database.daos;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public CategoryDM getCategoryDetails(Connection dbConn, String CategoryEnName) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append("      select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                "  MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL, MERCHANT_ITEM_CATEGORY.CATEGORY_CODE from MERCHANT_ITEM_CATEGORY where MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG ='"+CategoryEnName+"'");

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

        }
        return CategoryDM;
    }
    public CategoryDM getAllCategoryDetails(Connection dbConn) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append("      select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                "  MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL from MERCHANT_ITEM_CATEGORY where MERCHANT_ITEM_CATEGORY.MERCHANT_ID = 4606");

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

}
