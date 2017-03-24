package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import controller.ViewController;
import database.DatabaseManager;
import user.BusinessOwner;
import user.Customer;

public class ClientModel {

	private BusinessOwner businessOwner;
	private Customer customer;
	private ViewController viewController = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();

	private String username;
	private String password;
	Scanner sc = new Scanner(System.in);

	public ClientModel() {}

    /**
     * Pass the business owner to the model.
     * @param businessOwner The UserManager being passed.
     */
    public void initBusinessOwner(BusinessOwner businessOwner)
    {
        this.businessOwner = businessOwner;
    }
    /**
     * Pass the customer to the model.
     * @param customer The UserManager being passed.
     */
    public void initCustomer(Customer customer)
    {
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

		//get user input
		System.out.print("Username: ");
		username = sc.next();
		System.out.print("Password: ");
		password = sc.next();
		System.out.print("Confirm Password: ");
		String password2 = sc.next();

		//search the database and find if the username has not been used
		if(databaseManager.searchCustomerUserName(username) == false && databaseManager.searchBusinessUserName(username) == false){
			if(password.matches(password2)){
				//add username and password to the database
				databaseManager.insertIntoCustomer(username,password);
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
