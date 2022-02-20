package com.fawry.TicketsMall.angularAutomation.strategy.testData;

import java.util.ArrayList;


public interface TestDataStrategy {

	ArrayList<ArrayList<Object>> loadTestData(String connectionProperties);

	Object[][] getTestDataFromExtSource(String connectionProperties, Class dataModelClass);

}
