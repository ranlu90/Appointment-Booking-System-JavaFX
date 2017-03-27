package model;

import java.io.IOException;
import controller.ViewController;
import database.DatabaseManager;
import user.BusinessOwner;
import user.Customer;

/**
 *	System class, containing functions register and login.
 */
public class ClientModel {

	private ViewController view = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();
	private BusinessOwner businessOwner = new BusinessOwner();
	private Customer customer = new Customer();


	public ClientModel() {}

	/**
	 * Start the system, initiate the main menu by calling initMenu() in the inputController class
	 */
	public void start(){
		view.initMenu();
	}


	/**
	 * passing businessOwner from main method to this class when system starts.
	 * @param businessOwner initially null
	 */
	public void initBusinessOwner(BusinessOwner businessOwner){
		this.businessOwner = businessOwner;
	}


	/**
	 *	passing viewController from main method to this class when system starts.
	 */
	public void initViewController(ViewController view){
		this.view = view;
	}


	/**
	 * passing customer from main method to this class when system starts.
	 * @param customer initially null
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
     * Get user's input for username and password, if both are found in the database, go to client menus.
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


	/**
	 *	Insert user's input to Customerinfo table.
	 * @param firstname
	 * @param lastname
	 * @param address
	 * @param contactNumber
	 * @param username	Username only contains digits, letters, '@', '.' and no spaces.
	 * @param password	Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.
	 * @return true if registration is successful
	 */
	public boolean register(String firstname, String lastname, String address,
			String contactNumber, String username, String password){

		//search the database and find if the username has been used
		if(databaseManager.searchCustomerUserName(username) == false && databaseManager.searchBusinessUserName(username) == false){
				if(databaseManager.insertIntoCustomer(firstname,lastname,address,contactNumber,username,password)){
					System.out.println("Your customer account has been successfully created!");
					return true;
				}
				else{
					System.out.println("Insertion into Customer table failed!");
					return false;
				}

		}
		else{
			System.out.println("This username has already been taken!");
			return false;
		}
	}

}
