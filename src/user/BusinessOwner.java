package user;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class BusinessOwner {
	private String BusinessName;
	private String BusinessOwnerName;
	private String address;
	private String phone;
	private TextField username;
	private PasswordField password;

	public BusinessOwner(String BusinessName,String BusinessOwnerName,
						String address,String phone,TextField username,PasswordField password){
		this.BusinessName = BusinessName;
		this.BusinessOwnerName = BusinessOwnerName;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}

	public String getBusinessName(){
		return BusinessName;
	}

	public void setBusinessName(String BusinessName){
		this.BusinessName = BusinessName;
	}

	public String getBusinessOwnerName(){
		return BusinessOwnerName;
	}

	public void setBusinessOwnerName(String BusinessOwnerName){
		this.BusinessOwnerName = BusinessOwnerName;
	}

	public String getaddress(){
		return address;
	}

	public void setaddress(String address){
		this.address = address;
	}

	public String getphone(){
		return phone;
	}

	public void setphone(String phone){
		this.phone = phone;
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