package model;

import java.util.Scanner;
import controller.ViewController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import user.BusinessOwner;
import user.Customer;

public class ClientModel {

	private BusinessOwner businessOwner;
	private Customer customer;
	private ViewController viewController = new ViewController();

	private String username = null;
	private String password = null;
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

    public void options(){
    	int i = sc.nextInt();
		if(i == 1){
			businessLogin();
		}
		else if(i == 2){
			customerLogin();
		}
		else if(i == 3){
			register();
		}
		else{
			System.out.println("Invaild selection!");
			return;
		}
    }

    /**
     * Get user's input for username and password, if both are found in the database, go to main menu for business owner.
     */
	public void businessLogin()
	{
		//get user input
		System.out.print("Username: ");
		username = sc.next();
		System.out.print("Password: ");
		password = sc.next();

		if(username != null && password != null)
		{
			//get username from business owner database, if attemptLogin for business owner is true
			if(true){
				//businessOwner.setusername(username);
				viewController.gotoBusiness();
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
			if(true){
				//customer.setusername(username);
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
		if(true){
			if(password.matches(password2)){
				//add username and password to the database

				System.out.println("Your customer account has been successfully created!");
			}
			else
				System.out.println("Passwords does not match, please enter username and password.");
		}
		else
			System.out.println("This username has already been taken!");
	}
}
