package controller;

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


	public void login()
	{
		String userType;

		if(username.getText() != null && password.getText() != null)
		{
			//retrive usertype from database
			//userType =
			if(userType != null){
				if(userType.equals("businessOwner"))
				{
					businesOwner.setusername(username);
					viewController.gotoBusiness();
				}
				else if (userType.equals("customer")){
					customer.setusername(username);
					viewController.gotoCustomer();
				}
				else
					System.out.println("userType is not in the database!");
			}
			//If a user is not found neither in business owner database or customer database
			else
			{
				System.out.println("Incorrect credentials!");
			}
		}
	}



}