package model;

import java.io.IOException;
import controller.ViewController;
import database.DatabaseManager;
import user.BusinessOwner;
import user.Customer;

/**
 *	System class, containing functions register, login etc.
 */
public class ClientModel {

	private ViewController view = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();
	private BusinessOwner businessOwner = new BusinessOwner();
	private Customer customer = new Customer();


	public ClientModel() {}


	/**
	 * passing businessowner from main method to this class when system starts.
	 * @param businessOwner initial null variable
	 */
	public void initBusinessOwner(BusinessOwner businessOwner){
		this.businessOwner = businessOwner;
	}


	/**
	 * passing customer from main method to this class when system starts.
	 * @param customer initial null variable
	 */
	public void initCustomer(Customer customer){
		this.customer = customer;
	}


    /**
     * create appointment booking system datbase,
     * create tables for business and customerinfo and
     * insert entities for them.
     * @throws IOException
     */
    public void initDatabase() throws IOException{
    	databaseManager.deleteDatabase();
    	databaseManager.createNewDatabase("AppointmentBookingSystem.db");
    	databaseManager.createBusinessTable();
    	databaseManager.createCustomerInfoTable();
    	databaseManager.insertInitialEntitiesForBusiness();
    	databaseManager.insertInitialEntitiesForCustomerInfo();
    	System.out.println();
    }


    /**
     * Get user's input for username and password, if both are found in the database, go to relative menu.
     */
	public boolean login(String username, String password)
	{
		if(username != null && password != null)
		{
			if(databaseManager.searchBusiness(username,password) == true){
				businessOwner.setusername(username);
				businessOwner.setpassword(password);
				view.gotoBusiness();
				return true;
			}
			else if(databaseManager.searchCustomer(username, password) == true){
				customer.setusername(username);
				customer.setpassword(password);
				view.gotoCustomer();
				return true;
			}
			else{
				System.out.println("Incorrect credentials!");
				return false;
			}
		}
		return false;
	}


	//the user selected to register as a customer
	public boolean register(String firstname, String lastname, String address,
			String contactNumber, String username, String password){

		//search the database and find if the username has been used
		if(databaseManager.searchCustomerUserName(username) == false && databaseManager.searchBusinessUserName(username) == false){
				databaseManager.insertIntoCustomer(firstname,lastname,address,contactNumber,username,password);
				System.out.println("Your customer account has been successfully created!");
				return true;

		}
		else{
			System.out.println("This username has already been taken!");
			return false;
		}

	}

}
