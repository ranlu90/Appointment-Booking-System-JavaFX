package model;

import java.util.Scanner;
import controller.ViewController;
import database.DatabaseManager;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
			if(databaseManager.searchBusinessUserName(username) == true &&
					databaseManager.searchBusinessPassword(password) == true ){
				//businessOwner.setusername(username);
				if(username.equals("admin")&&password.equals("admin")){
				viewController.gotoBusiness();
				}
				else{
					viewController.gotoCustomer();
				}
			}
			else{
				System.out.println("Incorrect credentials!");
				return;
			}
		}
	}

	/**
	 * Get user's input for username and password, if both are found in the database, go to main menu for customer.
	 */
<<<<<<< HEAD
	public void customerLogin()
	{
		//get user input
		System.out.print("Username: ");
		username = sc.next();
		System.out.print("Password: ");
		password = sc.next();

		if(username != null && password != null)
		{
			//get username from customer info database, if attemptLogin for customer is true
			if(databaseManager.searchCustomerUserName(username) == true &&
					databaseManager.searchCustomerPassword(password) == true ){
				//customer.setusername(username);
				viewController.gotoCustomer();
			}
			else{
				System.out.println("Incorrect credentials!");
				return;
			}
		}
	}
=======
	
>>>>>>> origin/login

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
		if(databaseManager.searchCustomerUserName(username) == false){
			if(password.matches(password2)){
				//add username and password to the database
				databaseManager.insertIntoCustomer(username,password);
			}
			else{
				System.out.println("Passwords does not match, please enter username password again.");
		    register();
			}
		}
		else
			System.out.println("This username has already been taken!");
	}
}
