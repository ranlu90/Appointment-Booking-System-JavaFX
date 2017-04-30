package database;



/**
 * business owner information will be stored in Businessowner table.
 */
public class BusinessOwner {

	private String BusinessName;
	private String address;
	private String phone;
	private String day;
	private String time;

	//create default constructor to evoke methods in the class
	public BusinessOwner(){}


	public String getBusinessName(){
		return BusinessName;
	}
	public void setBusinessName(String BusinessName){
		this.BusinessName = BusinessName;
	}


	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}


	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getDay(){
		return day;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getTime(){
		return time;
	}

	public void setTime(String time){
		this.time = time;
	}
}