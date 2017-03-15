package controller;

import java.util.Scanner;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import user.BusinessOwner;
import user.Customer;

public class LoginController {

	private ViewController viewController;

	private BusinessOwner businesOwner;
	private Customer customer;
	private TextField username;
    private PasswordField password;

	//user select to go to business owner login menu
	public void businessLogin()
	{
		if(username.getText() != null && password.getText() != null)
		{
			//get username from business owner database, if attemptLogin for business owner is true
			if(userType != null){
				businesOwner.setusername(username);
				viewController.gotoBusiness();
			}
			else{
				System.out.println("Incorrect credentials!");
				return;
			}
		}
	}

	//user select to go to customer login menu
	public void customerLogin()
	{
		if(username.getText() != null && password.getText() != null)
		{
			//get username from customer info database, if attemptLogin for customer is true
			if(userType != null){
				customer.setusername(username);
				viewController.gotoCustomer();
			}
			else{
				System.out.println("Incorrect credentials!");
				return;
			}
		}
	}
}