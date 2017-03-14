package user;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Customer {
	private TextField username;
	private PasswordField password;

	public Customer(TextField username, PasswordField password){
		this.username = username;
		this.password = password;
	}

	public TextField getusername(){
		return username;
	}

	public void setusername(TextField username){
		this.username = username;
	}

	public PasswordField getpassword(){
		return password;
	}

	public void setpassword(PasswordField password){
		this.password = password;
	}
}
