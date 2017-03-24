package model;

import java.io.IOException;
import java.util.Scanner;
import controller.ViewController;
import database.DatabaseManager;


public class ClientModel {

	private ViewController viewController = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();

	private String username;
	private String password;
	Scanner sc = new Scanner(System.in);

	public ClientModel() {}


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
     * Get user's input for username and password, if both are found in the database, go to main menu for business owner.
     */
	public void login()
	{
		//get user input
		System.out.print("Username: ");
		username = sc.next();
		System.out.print("Password: ");
		password = sc.next();

		if(username != null && password != null)
		{
			if(databaseManager.searchBusiness(username,password) == true || username.equals("admin")){

				viewController.gotoBusiness();
			}
			else if(databaseManager.searchCustomer(username, password) == true || username.equals("customer")){

				viewController.gotoCustomer();
			}
			else{

				System.out.println("Incorrect credentials!");
				return;
			}
		}
	}


	//the user selected to register as a customer
	public void register(){

		//regular expression for password check
		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
		//get user input
		System.out.println("Password must contain one uppercase, one lowercase, a number and minimum length of 6.");
		System.out.print("Username: ");
		username = sc.next();
		System.out.print("Password: ");
		password = sc.next();
		if(!password.matches(check)){
			System.out.println("Please choose a more complicated password, return to the main menu.");
			return;
		}

		System.out.print("Confirm Password: ");
		String password2 = sc.next();

		//search the database and find if the username has not been used
		if(databaseManager.searchCustomerUserName(username) == false && databaseManager.searchBusinessUserName(username) == false){
			if(password.matches(password2)){
				//implement functions here for get first_name, last_name, address and contact_number


				//add username and password to the database
				//remove "//" when functions have been completed.
				//databaseManager.insertIntoCustomer(username,password,firstname,lastname,address,contactNumber);
			}
			else{
				System.out.println("Passwords does not match, return to the main menu.");
				return;
			}
		}
		else
			System.out.println("This username has already been taken!");
	}
}
