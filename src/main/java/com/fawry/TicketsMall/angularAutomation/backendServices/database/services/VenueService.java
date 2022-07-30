package com.fawry.TicketsMall.angularAutomation.backendServices.database.services;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.backendServices.database.daos.VenueDAO;
import com.fawry.TicketsMall.angularAutomation.constants.GeneralConstants;
import com.fawry.TicketsMall.angularAutomation.dataModels.VenueDM;

import java.sql.Connection;
import java.sql.SQLException;

public class VenueService {

    public VenueDM getVenueDetails( String venueEnName)throws SQLException, ClassNotFoundException {
        //Open connection to users database
        DBConnection conn = new DBConnection();
        Connection connection = conn.openConnection(GeneralConstants.CORE_DB_NAME);
        VenueDAO venueDAO = new VenueDAO();
       VenueDM venueDM = venueDAO.getVenueDetails(connection ,venueEnName);
        //close db connection
        conn.closeDBConnection(connection);
        return venueDM;
    }
}
