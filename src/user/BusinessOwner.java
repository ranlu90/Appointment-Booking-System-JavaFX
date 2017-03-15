package user;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class BusinessOwner {
	private String BusinessName;
	private String BusinessOwnerName;
	private String address;
	private String phone;
	private String username;
	private String password;

	/**
	 * business owner information will be stored in business owner database
	 */
	public BusinessOwner(String BusinessName,String BusinessOwnerName,
						String address,String phone,String username,String password){
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