package model;

import java.io.IOException;
import java.util.Scanner;
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

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String address;
	private String contactNumber;
	Scanner sc = new Scanner(System.in);

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
		this.username = username;
		this.password = password;
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
	public void register(){

		//regular expression for password check
		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
		//get user input
		System.out.print("Username: ");
		username = sc.nextLine();

		if(!username.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{1,}$")){
			System.out.println("Username cannot contain white spaces!");
			return;
		}
		System.out.println("Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.");
		System.out.print("Password: ");
		password = sc.nextLine();
		if(!password.matches(check)){
			System.out.println("Password is too easy, return to the main menu.");
			System.out.println("=========================================");
				return;
		}

		System.out.print("Confirm Password: ");
		String password2 = sc.nextLine();

		//search the database and find if the username has not been used
		if(databaseManager.searchCustomerUserName(username) == false && databaseManager.searchBusinessUserName(username) == false){
			if(password.matches(password2)){
				System.out.print("Please enter your first name: ");
				firstname = sc.nextLine();
				System.out.print("Please enter your last name: ");
				lastname = sc.nextLine();
				System.out.print("Please enter your address: ");
				address = sc.nextLine();
				System.out.print("Please enter your contact number: ");
				contactNumber = sc.nextLine();

				if(databaseManager.insertIntoCustomer(firstname,lastname,address,contactNumber,username,password)){
					System.out.println("Your customer account has been successfully created!");
					return;
				}
			}
			else{
				System.out.println("Passwords does not match, return to the main menu.");
				return;
			}
		}
		else
			System.out.println("This username has already been taken!");
			return;
		}

}
