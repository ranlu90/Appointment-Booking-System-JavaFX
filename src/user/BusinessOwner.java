package user;

import java.util.ArrayList;
import java.util.Date;
import business.Employee;

/**
 * business owner information will be stored in Businessowner table.
 */
public class BusinessOwner {
	private String BusinessName;
	private String BusinessOwnerName;
	private String address;
	private String phone;
	//The predefined working hours in a week, defined by business owner upon business registration.
	private String workingHours;
	private String username;
	private String password;
	//the list containing all employee information
	private ArrayList<Employee> employeeList = new ArrayList<Employee>();
	//the actual working date in a month
	private ArrayList<Date> actualWorkingHours = new ArrayList<Date>();

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

	public String getWorkingHours(){
		return workingHours;
	}

	public void setWorkingHours(String workingHours){
		this.workingHours = workingHours;
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

	public ArrayList<Date> getActualWorkingHours(){
		return actualWorkingHours;
	}

	public void setActualWorkingHours(ArrayList<Date> actualWorkingHours){
		this.actualWorkingHours = actualWorkingHours;
	}
}