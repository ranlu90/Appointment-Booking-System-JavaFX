package database;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;


/**
 * business owner information will be stored in Businessowner table.
 */
public class BusinessOwner {

	private String BusinessName;
	private String BusinessOwnerName;
	private String address;
	private String phone;

	//The predefined business hours in a week, defined by business owner upon business registration.
	private String businessHours;
	private String username;
	private String password;

	//the list containing all employee information
	private ArrayList<Employee> employeeList = new ArrayList<Employee>();

	/**
	 * The first variable 'Date' stores the business owner's selected business days in a month.
	 * The second variable 'HashMap<Integer,Date>' stores actual business hours in a particular day,
	 * 'Integer' - '0' the open time, '1' the closing time; 'Date' the actual time variable.
	 */
	private HashMap<Date,HashMap<Integer,Date>> businessSchedule = new HashMap<Date,HashMap<Integer,Date>>();


	//create default constructor to evoke methods in the class
	public BusinessOwner(){}


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

	public String getBusinessHours(){
		return businessHours;
	}

	public void setBusinessHours(String businessHours){
		this.businessHours = businessHours;
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

	public ArrayList<Employee> getEmployeeList(){
		return employeeList;
	}

	public void setEmployeeList(ArrayList<Employee> employeeList){
		this.employeeList = employeeList;
	}

	public HashMap<Date,HashMap<Integer,Date>> getActualBusinessHours(){
		return businessSchedule;
	}

	public void setActualBusinessHours(HashMap<Date,HashMap<Integer,Date>> businessSchedule){
		this.businessSchedule = businessSchedule;
	}
}