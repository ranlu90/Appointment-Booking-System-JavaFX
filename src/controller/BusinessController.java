package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import database.DatabaseManager;

/**
 * Include functions for business owner, provide fundamental methods for viewController, add information to business owner.
 * Parameters received from ViewController class, managing information in business owner.
 * @author ranlu
 *
 */
public class BusinessController {

	private DatabaseManager databaseManager;
	private String username;
	private static Scanner sc = new Scanner(System.in);

	public BusinessController(){}


	/**
	 * set database for business owner
	 * @param database get from clientModel
	 */
	public void setDatabaseManager(DatabaseManager databaseManager){
		this.databaseManager = databaseManager;
	}

	public void setUsername(String username){
		this.username = username;
	}


	/**
	 * from each business owner, add employee information
	 * @return true if adding info successfully
	 */
	public boolean addEmployee(String firstname,String lastname,String owner_username,String email,
			String contact_number,String workingDay,String workingTime){
		if(email.matches("([0-9a-zA-Z._-]+)@((?:[0-9a-zA-Z]+.)+)([a-zA-Z]{2,4})")){
			if(!databaseManager.searchEmployeeEmail(email)){
				System.out.println("Please select one of the following options:");
				System.out.println("S - Store the employee information");
				System.out.println("X - Quit without saving any information");
				String line = sc.nextLine();
				if(line.equalsIgnoreCase("S")){
					databaseManager.setEmployee(firstname, lastname, owner_username, email, contact_number);
					databaseManager.setWorkingTime(workingDay, workingTime, email);
					System.out.println("The information have been added to your actual business time.\n");
					return true;
				}
				else{
					System.out.println("Information wasn't stored, return to the business menu.\n");
					return false;
				}
			}
			else{
				System.out.println("Email already exists!");
				return false;
			}
		}
		else{
			System.out.println("Email address format is invalid!");
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
				System.out.println("S - Store the business time");
				System.out.println("X - Quit without saving any information");
				String line = sc.nextLine();
				if(line.equalsIgnoreCase("S")){
					databaseManager.setBusinessTime(business_date, owner_username, open_time, closing_time);
					System.out.println("The information have been added to your actual business time.\n");
					return true;
				}
				else{
					System.out.println("Information wasn't stored, return to the business menu.\n");
					return false;
				}
	    }catch (ParseException e) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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
	 * @throws SQLException
	 */
	public boolean viewWorkersAvailability(){
		try{
			ResultSet result = databaseManager.getEmployee(username);
			//move cursor to the last object and get the row number, therefore get the number of rows in the table, starting from 1.
			result.last();
			int size = result.getRow();
			System.out.println("Your emloyees' working days and time are as following:");
			System.out.println("=========================");
			for(int i = 1; i < size; i ++){
				//move cursor to the current employee object
				result.absolute(i);
				System.out.println(result.getString("first_name") + " " + result.getString("last_name"));
				System.out.println(result.getString("email"));
				System.out.println(result.getString("contact_number"));
				System.out.println("Working Days/Time:");
				ResultSet workingTime = databaseManager.getWorkingTime(result.getString("email"));
				workingTime.last();
				int sizeOfTable = workingTime.getRow();
				for(int j = 1; j < sizeOfTable; j ++){
					System.out.println(workingTime.getString("day") + "  " + workingTime.getString("time"));
				}
				System.out.println("=========================");
			}
			return true;
		}
		catch(NullPointerException | SQLException e){
			System.out.println("No employees in your employee list.\n");
			return false;
		}
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


	/**
	 * Get user's input from console for add employee.
	 */
	public void employeeInput(){
		try{
		String firstname;
		String lastname;
		String email;
		String contactNumber;
		String workingDay;
		String workingTime;
		String owner_username = username;

		String week[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		String period[] = {"9:00 - 11:00","11:00 - 13:00","13:00 - 15:00","15:00 - 17:00"};
		;
		System.out.println("You have chosen option A: Add a new employee.");
		System.out.println("Please enter the emplyee's first name:");
		firstname = sc.nextLine();
		System.out.println("Please enter the emplyee's last name:");
		lastname = sc.nextLine();
		System.out.println("Please enter the emplyee's e-mail address:");
		email = sc.nextLine();
		System.out.println("Please enter the emplyee's contact number:");
		contactNumber = sc.nextLine();
		System.out.println("Please select the emplyee's working day in a week:");
		System.out.println("1.Monday  2.Tuesday  3.Wednesday  4.Thursday  5.Friday  6.Saturday  7.Sunday");
		workingDay = week[Integer.parseInt(sc.nextLine()) -1];
		System.out.println("Please select the emplyee's working time in a day:");
		System.out.println("1. 9:00-11:00  2. 11:00-13:00  3. 13:00-15:00  4. 15:00-17:00");
		workingTime = period[Integer.parseInt(sc.nextLine()) -1];
		addEmployee(firstname,lastname,owner_username,email,contactNumber, workingDay, workingTime);
		}catch(Exception e){
			System.out.println("Invalid input!");
			return;
		}
	}
}
