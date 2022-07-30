package com.fawry.TicketsMall.angularAutomation.backendServices.database.daos;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.DBConnection;
import com.fawry.TicketsMall.angularAutomation.constants.database.VenueDBTable;
import com.fawry.TicketsMall.angularAutomation.dataModels.VenueDM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenueDAO {

    public VenueDM getVenueDetails(Connection dbConn, String venueEnName) throws SQLException {

        //Create DB Query to selected added/updated Category
        StringBuilder query = new StringBuilder();
        query.append("Select v.venue_name_en as NAME_EN , v.description_en as DESCRIPTION_EN ," +
                " v.venue_name_ar as NAME_AR , v.description_ar as DESCRIPTION_AR ,v.location as LOCATION ," +
                " v.seated as SEATED_FLAG , v.seat_map as SEAT_MAP , t.category_name as CATEGORY_NAME ," +
                " t.total_number_of_seats as NUMBER_OF_SEATS " +
                " From venue v " +
                " Left join ticket_category t on t.venue_id=v.id " +
                " where v.venue_name_en ='"+venueEnName+"'");

        // Execute query
        DBConnection conn = new DBConnection();
        ResultSet venueRS = conn.executeQueryAndGetRS(dbConn, query.toString());

        // fill data returned from DB into data model
        VenueDM venueDM = null;

        if (venueRS.next())
        {
            venueDM = new VenueDM();
            venueDM.setNameEn(venueRS.getString(VenueDBTable.NAME_EN)==null?"":venueRS.getString(VenueDBTable.NAME_EN));
            venueDM.setDescriptionEn(venueRS.getString(VenueDBTable.DESCRIPTION_EN)==null?"":venueRS.getString(VenueDBTable.DESCRIPTION_EN));
            venueDM.setNameAr(venueRS.getString(VenueDBTable.NAME_AR)==null?"":venueRS.getString(VenueDBTable.NAME_AR));
            venueDM.setDescriptionAr(venueRS.getString(VenueDBTable.DESCRIPTION_AR)==null?"":venueRS.getString(VenueDBTable.DESCRIPTION_AR));
            venueDM.setLocationDesc(venueRS.getString(VenueDBTable.LOCATION)==null?"":venueRS.getString(VenueDBTable.LOCATION));
            venueDM.setSeatedFlag(venueRS.getString(VenueDBTable.SEATED_FLAG)==null?"":venueRS.getString(VenueDBTable.SEATED_FLAG));
            venueDM.setSeatMap(venueRS.getString(VenueDBTable.SEAT_MAP)==null?"":venueRS.getString(VenueDBTable.SEAT_MAP));
            venueDM.setCatName(venueRS.getString(VenueDBTable.CATEGORY_NAME)==null?"":venueRS.getString(VenueDBTable.CATEGORY_NAME));
            venueDM.setNumOfTickets(venueRS.getString(VenueDBTable.NUMBER_OF_SEATS)==null?"":venueRS.getString(VenueDBTable.NUMBER_OF_SEATS));

        }
        return venueDM;
    }

}
