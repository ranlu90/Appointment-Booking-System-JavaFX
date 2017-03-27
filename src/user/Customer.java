package user;

public class Customer {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String address;
	private String contactNumber;

	/**
	 * customer information will be stored in customer info database
	 */
	public Customer(){}

	public Customer(String firstname, String lastname, String address, String contactNumber, String username,
			String password){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.contactNumber = contactNumber;
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

	public String getfirstname(){
		return firstname;
	}

	public void setfirstname(String firstname){
		this.firstname = firstname;
	}

	public String getlastname(){
		return lastname;
	}

	public void setlastname(String lastname){
		this.lastname = lastname;
	}

	public String getaddress(){
		return address;
	}

	public void setaddress(String address){
		this.address = address;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}
}
