package com.fawry.TicketsMall.angularAutomation.backendServices.database.services;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.backendServices.database.daos.CategoryDAO;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.CategoryDM;

import java.sql.Connection;
import java.sql.SQLException;

public class CategoryService {

    public CategoryDM getCategoryDetails(String CategoryEnName)throws SQLException, ClassNotFoundException {
        //Open connection to users database
        DBConnection conn = new DBConnection();
        Connection connection = conn.openConnection(GeneralConstants.CORE_DB_NAME);
        CategoryDAO CategoryDAO = new CategoryDAO();
        CategoryDM CategoryDM = CategoryDAO.getCategoryDetails(connection ,CategoryEnName);
        //close db connection
        conn.closeDBConnection(connection);
        return CategoryDM;
    }
    public CategoryDM getAllCategoryDetails()throws SQLException, ClassNotFoundException {
        //Open connection to users database
        DBConnection conn = new DBConnection();
        Connection connection = conn.openConnection(GeneralConstants.CORE_DB_NAME);
        CategoryDAO CategoryDAO = new CategoryDAO();
        CategoryDM CategoryDM = CategoryDAO.getAllCategoryDetails(connection );
        //close db connection
        conn.closeDBConnection(connection);
        return CategoryDM;
    }



    public CategoryDM getCategoryStatus(String CategoryEnName)throws SQLException, ClassNotFoundException {
        //Open connection to users database
        DBConnection conn = new DBConnection();
        Connection connection = conn.openConnection(GeneralConstants.CORE_DB_NAME);
        CategoryDAO CategoryDAO = new CategoryDAO();
        CategoryDM CategoryDM = CategoryDAO.getCategoryStatus(connection ,CategoryEnName);
        //close db connection
        conn.closeDBConnection(connection);
        return CategoryDM;
    }
}
