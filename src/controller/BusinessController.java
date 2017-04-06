package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import business.Employee;
import database.DatabaseManager;
import user.BusinessOwner;

/**
 * Include functions for business owner, provide fundamental methods for viewController, add information to business owner.
 * Parameters received from ViewController class, managing information in business owner.
 * @author ranlu
 *
 */
public class BusinessController {

	private DatabaseManager databaseManager;
	//business owner list, using business owner's username as a key for each entity in the list.
	private	HashMap<String,BusinessOwner> businessOwnerList = new HashMap<String,BusinessOwner>();
	private BusinessOwner businessOwner = new BusinessOwner();
	//get the username from ClientModel class
	private String username;
	private static Scanner sc = new Scanner(System.in);

	public BusinessController(){}


	/**
	 * set database for business owner
	 * @param database get from clientModel
	 */
	public void setDatabaseManager(DatabaseManager database){
		this.databaseManager = database;
	}

	public void setUsername(String username){
		this.username = username;
	}
	/**
	 * from each business owner, add employee information
	 * @return true if adding info successfully
	 */
	public boolean addEmployee(){

		String line;

		//capture user's input in the console
		Employee employee = new Employee();
		String firstname;
		String lastname;
		String email;
		String contactNumber;
		String workingDay;
		String workingTime;

		System.out.println("You have chosen option A: Add a new employee.");
		System.out.println("Please enter the emplyee's first name:");
		firstname = sc.nextLine();
		employee.setFirstName(firstname);

		System.out.println("Please enter the emplyee's last name:");
		lastname = sc.nextLine();
		employee.setLastName(lastname);

		System.out.println("Please enter the emplyee's e-mail address:");
		email = sc.nextLine();
		if(email.matches("([0-9a-zA-Z._-]+)@((?:[0-9a-zA-Z]+.)+)([a-zA-Z]{2,4})")){
			if(!searchExistingEmployee(email)){
				employee.setEmail(email);
				System.out.println("Please enter the emplyee's contact number:");
				contactNumber = sc.nextLine();
				employee.setContactNumber(contactNumber);
				do{

					System.out.println("Please enter the emplyee's working day in a week:");
					workingDay = sc.nextLine();
					if(workingDay.matches("Monday")||workingDay.matches("Tuesday")||workingDay.matches("Wednesday")
							||workingDay.matches("Thursday")||workingDay.matches("Friday")){
						System.out.println("Please enter the emplyee's working period in a day:");
					workingTime = sc.nextLine();
					if(workingTime.matches("[0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2}")){
					employee.getWorkingSchedule().put(workingDay, workingTime);
					System.out.println("Please select one of the following options:");
					System.out.println("A - Add more working days/time");
					System.out.println("S - Store the employee's details");
					System.out.println("X - Quit without saving any information");
					}
					}
					line = sc.nextLine();

					if(line.equalsIgnoreCase("X")){
						System.out.println("Information wasn't stored, return to the business menu.\n");
						return false;

					}


				}while(!line.equalsIgnoreCase("S"));

				businessOwner.getEmployeeList().add(employee);
				//update business owner information for a given username
				businessOwnerList.put(username, businessOwner);
				System.out.println("The employee's details have been added to your employee list.\n");
				return true;
			}


			else{
				System.out.println("An employee with the same email address exists!");
				return false;
			}

		}
		else{
			System.out.println("email address format is invalid!");
			return false;
		}
	}


	/**
	 *
	 */
	public boolean addBusinessTime(String business_date, String owner_username, String open_time, String closing_time){

		SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	    try {
				Date day = dateFormat.parse(business_date);
				Date open = timeFormat.parse(open_time);
				Date close = timeFormat.parse(closing_time);
				System.out.println("Please select one of the following options:");
				System.out.println("S - Store the business hours");
				System.out.println("X - Quit without saving any information:");
				String line = sc.nextLine();
				if(line.equalsIgnoreCase("S")){
					databaseManager.setBusinessTime(business_date, owner_username, open_time, closing_time);
					System.out.println("The information have been added to your actual business hours.\n");
					return true;

				}
				else{
					System.out.println("Information wasn't stored, return to the business menu.\n");
					return false;
				}

	    }catch (ParseException e) {
	    	System.out.println("Not a valid date/time format, return to the business menu.");
	    	return false;
	    }
	}


	/**
	 *
	 */
	public boolean viewAllBookings(){
		return false;
	}


	/**
	 *
	 */
	public boolean viewNewBookings(){
		return false;
	}


	/**
	 *	print all worker's name, email, contact number and working days/time in a week.
	 */
	public boolean viewWorkersAvailability(){
		//retrieve data for a specific business owner
		BusinessOwner temp = businessOwnerList.get(username);

		try{
			System.out.println("Your emloyees' working days and time are as following:");
			System.out.println("=========================");
			for(Employee employee : temp.getEmployeeList()){
				System.out.println(employee.getFirstName() +" " + employee.getLastName());
				System.out.println(employee.getEmail());
				System.out.println(employee.getContactNumber());
				System.out.println("Working Days/Time:");
				for(String day : employee.getWorkingSchedule().keySet()){
					String time = employee.getWorkingSchedule().get(day);
					System.out.println(day + "  " + time);
				}
				System.out.println("=========================");
			}
			return true;
		}
		catch(NullPointerException e){
			System.out.println("No employees in your employee list.\n");
			return false;
		}
	}


	/**
	 * Search the business owner's employee list to find if a specific employee exists.
	 * @param email	Use email as the primary key, each employee's email is unique.
	 * @return	true if employee exists, otherwise false.
	 */
	public boolean searchExistingEmployee(String email){
		for(Employee employee : businessOwner.getEmployeeList()){
			if(email.equalsIgnoreCase(employee.getEmail())){
				return true;
			}
		}
		return false;
	}


	/**
	 * Get user's input for add business time from console
	 */
	public void businessTimeInput(){
		String business_date;
		String owner_username = username;
		String open_time;
		String closing_time;

		System.out.println("You have chosen option B: Add working time/dates for the next month.");
		System.out.println("Please enter dates with format (DD/MM): ");
		business_date = sc.nextLine();
		System.out.println("Please enter business open time with 24-hour format (HH:mm): ");
		open_time = sc.nextLine();
		System.out.println("Please enter business closing time with 24-hour format (HH:mm): ");
		closing_time = sc.nextLine();
		addBusinessTime(business_date, owner_username, open_time, closing_time);
	}
}
