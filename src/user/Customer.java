package user;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Customer {
	private String username;
	private String password;

	/**
	 * customer information will be stored in customer info database
	 */
	public Customer(String username, String password){
		this.username = username;
		this.password = password;
	}

	public String getusername(){
		return username;
	}

	public void setusername(String username){
		this.username = username;
	}

	public String getpassword(){
		return password;
	}

	public void setpassword(String password){
		this.password = password;
	}
}
