package com.fawry.TicketsMall.angularAutomation.backendServices;

import com.fawry.TicketsMall.angularAutomation.backendServices.database.services.VenueService;
import com.fawry.TicketsMall.angularAutomation.dataModels.VenueDM;
import com.fawry.TicketsMall.angularAutomation.utils.Log;

public class ServicesDelegate {

    public ServicesDelegate()
    {
        Log.info(" *******   Create an instance of ServiceDelegate to access backend DB    ******** ");
    }



    public VenueDM getVenueDetails(String venueEnName)
    {
       VenueDM venueDM= null;
        try {
            VenueService venueService = new VenueService();
            venueDM = venueService.getVenueDetails(venueEnName);

        }

        catch (Exception e)
        {
            Log.error("ERROR occured in " + new Object() {}
                    .getClass().getName() + "." + new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
        return venueDM;
    }




}
