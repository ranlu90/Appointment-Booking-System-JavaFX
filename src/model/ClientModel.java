package model;


import java.io.IOException;

import java.util.Scanner;
import controller.ViewController;
import database.DatabaseManager;


/**
 *	System class, containing functions register and login.
 *	Get the user's input, move to login or register.
 *	If login is successful, go to business or customer menu.
 */
public class ClientModel {

	private ViewController view = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();
	private static Scanner sc = new Scanner(System.in);


	public ClientModel() {}

	/**
	 * Start the system, initiate the main menu by calling initMenu() in the inputController class
	 */
	public void initMenu(){
		String input;
		char selection = '\0';
	    do{
	            // display menu options
				System.out.println("\n"+"======================================================");
	            System.out.println("Welcome to Appointment Booking System!");
	            System.out.println("Please select one of the following options:");
	            System.out.println("A - Login");
	            System.out.println("B - Register");
	            System.out.println("X - Exit Program");

	            // prompt the user to enter their selection
	            System.out.print("Enter your selection: ");
	            input = sc.nextLine();

	            System.out.println();

	            if (input.length() != 1)
	            {
	                System.out.println("Error - selection must be a single character!");
	            } else
	            {
	                // extract the user's menu selection as a char value and
	                // convert it to upper case so that the menu becomes
	                // case-insensitive

	                selection = Character.toUpperCase(input.charAt(0));

	                // process the user's selection
	                switch (selection)
	                {
	                case 'A':
	                	getLoginInput();
	                	break;

	                case 'B':
	                	getRegisterInput();
	                    break;

	                case 'X':
	                    System.out.println("Booking system shutting down and goodbye!");
	                    break;

	                default:
	                    // default case - handles invalid selections
	                    System.out.println("Error - invalid selection!");
	                }
	            }
	            System.out.println();
	    }
	    while (selection != 'X');
	}


	/**
	 * Get user input for login, then go to login function to check if username and password matches.
	 */
	public void getLoginInput(){
		System.out.print("Username: ");
		String username = sc.nextLine();
		System.out.print("Password: ");
		String password = sc.nextLine();
		login(username,password);
	}


	/**
	 * Get user input for register, then go to register function to check if username already exists.
	 */
	public void getRegisterInput(){
		System.out.print("Username: ");
		String username = sc.nextLine();
		System.out.println("Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.");
		System.out.print("Password: ");
		String password = sc.nextLine();
		System.out.print("Confirm Password: ");
		String password2 = sc.nextLine();
		System.out.print("Please enter your first name: ");
		String firstname = sc.nextLine();
		System.out.print("Please enter your last name: ");
		String lastname = sc.nextLine();
		System.out.print("Please enter your address: ");
		String address = sc.nextLine();
		System.out.print("Please enter your contact number: ");
		String contactNumber = sc.nextLine();
    	if(password.matches(password2)){
			register(firstname,lastname,address,contactNumber,username,password);
		}
    	else{
    		System.out.println("Passwords don't match!");
    		return;
    	}
	}


    /**
     * create appointment booking system datbase,
     * create all tables and insert entities for them.
     * @throws IOException If any exceptions occur.
     */
    public void initDatabase() throws IOException{

    	databaseManager.deleteDatabase();
    	databaseManager.createNewDatabase("AppointmentBookingSystem.db");

    	databaseManager.setConnection();
    	databaseManager.createBusinessTable();
    	databaseManager.createCustomerInfoTable();
    	databaseManager.createEmployeeTable();
    	databaseManager.createBusinessTimeTable();
    	databaseManager.createWorkingTimeTable();
    	databaseManager.createBookingTable();

    	databaseManager.insertInitialEntitiesForBooking();
    	databaseManager.insertInitialEntitiesForBusiness();
    	databaseManager.insertInitialEntitiesForCustomerInfo();

    }


    /**
     * Get user's input for username and password, if both are found in the database, go to business owner or client menu.
     */
	public boolean login(String username, String password)
	{
		view.setDatabaseManager(databaseManager);

		if(username != null && password != null)
		{
			if(databaseManager.searchBusiness(username,password) == true){

				//go to business owner menu
				System.out.println("\n"+"======================================================");
				System.out.println("Welcome! '"+ databaseManager.getBusinessName(username)  + "'.\n");
				view.setUserName(username);
				view.gotoBusiness();
				return true;
			}
			else if(databaseManager.searchCustomer(username, password) == true){

				System.out.println("\n"+"======================================================");
				System.out.println("Welcome! '"+ databaseManager.getCustomerName(username)  + "'.\n");
				//go to customer menu
				view.setUserName(username);
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
		if(username.matches("^[0-9a-zA-Z@\\.]{1,}$")){
			if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")){
				if(contactNumber.matches("([0-9+]*[ ()]*[0-9]*[ ()]*[0-9]*[ -]*[0-9]+)")){
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
				else
					System.out.println("Contact number format is invalid!");
					return false;
			}
			else{
				System.out.println("Password is too easy, return to the main menu.");
				return false;
			}
		}
		else{
			System.out.println("Username only contains digits, letters, '@', '.' and no spaces!");
			return false;
		}
	}
}
