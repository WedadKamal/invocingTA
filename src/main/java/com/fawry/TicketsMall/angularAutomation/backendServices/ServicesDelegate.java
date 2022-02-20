package com.fawry.TicketsMall.angularAutomation.backendServices;

import com.fawry.TicketsMall.angularAutomation.utils.Log;

public class ServicesDelegate {

    public ServicesDelegate()
    {
        Log.info(" *******   Create an instance of ServiceDelegate to access backend DB    ******** ");
    }



    /*public List<LookUpModel> getSKUProgs(String partnerMail)
    {
        List<LookUpModel> loyaltyProgs = null;
        try {
            LoyaltyProgService lpService = new LoyaltyProgService();
            loyaltyProgs = lpService.getSKUProgs(partnerMail);

        }

        catch (Exception e)
        {
            Log.error("ERROR occured in " + new Object() {}
                    .getClass().getName() + "." + new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
        return loyaltyProgs;
    }*/




}
