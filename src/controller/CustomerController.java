package controller;


import java.util.ArrayList;
import database.DatabaseManager;

/**
 * Include functions for customer, provide fundamental methods for viewController, add information to customer.
 * Parameters received from ViewController class, managing information in customer.
 * @author ranlu
 */
public class CustomerController {

	private String username;
	private DatabaseManager databaseManager;

	public CustomerController(){}


	/**
	 * set database for business owner
	 * @param database get from clientModel
	 */
	public void setDatabaseManager(DatabaseManager database){
		this.databaseManager = database;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * Customer can view available booking days and time.
	 */
	public void viewBookingAvailability(){
		System.out.println("Available booking dates and time for 'owner' are:");
		System.out.println("business_date  open_time   closing_time");
		System.out.println("-------------  ----------  ------------");
		ArrayList<ArrayList<String>> businessTime = databaseManager.getBusinessTime("owner");
		for(ArrayList<String> temp : businessTime){
			System.out.println(temp.get(0) + "          " + temp.get(1) + "         "  + temp.get(2));
		}
	}
}
