package controller;

import database.DatabaseManager;
import user.Customer;

/**
 * Include functions for customer, provide fundamental methods for viewController, add information to customer.
 * Parameters received from ViewController class, managing information in customer.
 * @author ranlu
 */
public class CustomerController {

	private Customer customer = new Customer();
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
	/**
	 * Customer can view available booking days and time.
	 */
	public void viewBookingAvailability(){

	}

	public void setUserName(String username) {
		this.username = username;
	}
}
