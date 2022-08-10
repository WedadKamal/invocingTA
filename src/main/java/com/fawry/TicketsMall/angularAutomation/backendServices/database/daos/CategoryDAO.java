package com.fawry.TicketsMall.angularAutomation.backendServices.database.daos;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.constants.database.CategoryDBTable;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    public CategoryDM getCategoryDetails(Connection dbConn, String CategoryEnName) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append("      select MERCHANT_ITEM_CATEGORY.ID ,MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG, MERCHANT_ITEM_CATEGORY.NAME_SECONDARY_LANG,\n" +
                "  MERCHANT_ITEM_CATEGORY.CATEGORY_IMAGE_URL from MERCHANT_ITEM_CATEGORY where MERCHANT_ITEM_CATEGORY.NAME_PRIMARY_LANG ='"+CategoryEnName+"'");

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

        }
        return CategoryDM;
    }

}
