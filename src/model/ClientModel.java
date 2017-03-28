package model;

import java.io.IOException;
import java.util.Scanner;

import controller.ViewController;
import database.DatabaseManager;
import user.BusinessOwner;
import user.Customer;

/**
 *	System class, containing functions register and login.
 *	Get the user's input, move to login or register.
 *	If logging is successful, go to business or customer menu.
 */
public class ClientModel {


	private ViewController view = new ViewController();
	private DatabaseManager databaseManager = new DatabaseManager();
	private BusinessOwner businessOwner = new BusinessOwner();
	private Customer customer = new Customer();
	private static Scanner sc = new Scanner(System.in);


	public ClientModel() {}

	/**
	 * Start the system, initiate the main menu by calling initMenu() in the inputController class
	 */
	public void initMenu(){

		String input;
		String username;
		String password;
		String firstname;
		String lastname;
		String address;
		String contactNumber;

		char selection = '\0';
	    do
	        {
	            // display menu options
				System.out.println("\n"+"====================================================");
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
	                System.out
	                        .println("Error - selection must be a single character!");

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
	            		//get user input
	            		System.out.print("Username: ");
	            		username = sc.nextLine();
	            		System.out.print("Password: ");
	            		password = sc.nextLine();

	                    login(username,password);
	                    break;

	                case 'B':
	            		//regular expression for password check
	            		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
	            		//get user input
	            		System.out.print("Username: ");
	            		username = sc.nextLine();
	            		//^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{1,}$
	            		if(!username.matches("^[0-9a-zA-Z@\\.]{1,}$")){
	            			System.out.println("Username only contains digits, letters, '@', '.' and no spaces!");
	            			break;
	            		}
	            		System.out.println("Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.");
	            		System.out.print("Password: ");
	            		password = sc.nextLine();
	            		if(!password.matches(check)){
	            			System.out.println("Password is too easy, return to the main menu.");
	            			System.out.println("=========================================");
	            			break;
	            		}
	            		System.out.print("Confirm Password: ");
	            		String password2 = sc.nextLine();
	            		if(password.matches(password2)){
	        				System.out.print("Please enter your first name: ");
	        				firstname = sc.nextLine();
	        				System.out.print("Please enter your last name: ");
	        				lastname = sc.nextLine();
	        				System.out.print("Please enter your address: ");
	        				address = sc.nextLine();
	        				System.out.print("Please enter your contact number: ");
	        				contactNumber = sc.nextLine();

	        				register(firstname,lastname,address,contactNumber,username,password);
	            		}
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

	        } while (selection != 'X');
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
				//go to business owner menu
				System.out.println("\n"+"==================================================================");
				System.out.println("You have logined in the business menu as '"+ databaseManager.getBusinessName(username)  + "'.\n");
				view.gotoBusiness();
				return true;
			}
			else if(databaseManager.searchCustomer(username, password) == true){
				customer.setusername(username);
				customer.setpassword(password);
				System.out.println("\n"+"==================================================================");
				System.out.println("You have logined in the customer menu as '"+ databaseManager.getCustomerName(username)  + "'.\n");
				//go to customer menu
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
