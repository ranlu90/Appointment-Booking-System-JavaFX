package user;

public class Customer {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String address;
	private int number;

	/**
	 * customer information will be stored in customer info database
	 */
	public Customer(String username, String password, String fistname, String lastname, String address,
			int number){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.number = number;
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
	
	public int getnumber(){
		return number;
	}

	public void setnumber(int number){
		this.number = number;
	}
}
