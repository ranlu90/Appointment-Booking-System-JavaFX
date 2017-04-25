package controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import database.DatabaseManager;

/**
 * Include functions for business owner, provide fundamental methods for viewController, add information to Business table,
 * businessTime table, Employee Table and WorkingTime table in the database.
 * databaseManger and username received from ViewController class.
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
	 * @param database get from viewController
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
			if(contact_number.matches("([0-9+]*[ ()]*[0-9]*[ ()]*[0-9]*[ -]*[0-9]+)")){
				if(!databaseManager.searchEmployeeEmail(email)){
					databaseManager.setEmployee(firstname, lastname, owner_username, email, contact_number);
					System.out.println("The employee's information have been added to your account.\n");
					return true;
				}
				else{
					System.out.println("Email already exists!");
					return false;
				}
			}
			else{
				System.out.println("contact number is invalid!");
				return false;
			}
		}
		else{
			System.out.println("Email address format is invalid!");
			return false;
		}
	}



	/**
	 * insert business time information into the database.
	 */
	public boolean addBusinessTime(String business_day, String owner_username, String open_time, String closing_time){
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	    try {
				timeFormat.parse(open_time);
				timeFormat.parse(closing_time);
				databaseManager.setBusinessTime(business_day, owner_username, open_time, closing_time);
				System.out.println("The information have been added to your actual business time.\n");
				return true;
	    }catch (ParseException e) {
	        System.out.println("Invalid data/time format.");
	    	return false;
	    }
	}


	/**
	 * Get all bookings for the current business owner.
	 * @return true if bookings are found.
	 */
	public boolean viewAllBookings(){
		System.out.println("You have chosen option C - View the summaries of bookings\n");
		try {
			ArrayList<ArrayList<String>> booking = databaseManager.getBookingForBusiness(username);
			System.out.println("Summary of Booking information are shown as following:");
			System.out.println("==========================================");
			for(ArrayList<String> temp : booking){
				ArrayList<String> customerinfo = databaseManager.getCustomerinfo(temp.get(4));
				System.out.println(temp.get(0) + " "+ temp.get(1));
				System.out.println(customerinfo);
		    System.out.println("==========================================");
			}

			return true;
		}
		catch(NullPointerException e){
			System.out.println("No bookings in your database.\n");
			return false;
		}
	}


	/**
	 * View all bookings after current date and time.
	 * @return true if bookings exist and print to the console.
	 */
	public boolean viewNewBookings(){
		try {
			ArrayList<ArrayList<String>> bookingList = databaseManager.getBookingForBusiness(username);
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

			System.out.println("The New Booking information as following:");
			System.out.println("==========================================");
			for(ArrayList<String> temp : bookingList){
				ArrayList<String> customerinfo = databaseManager.getCustomerinfo(temp.get(4));
				Date date = df.parse(temp.get(0));
				Date time = tf.parse(temp.get(1));
				Date currentDate = df.parse(df.format(new Date()));
				Date currentTime = tf.parse(tf.format(new Date()));
				if( date.compareTo(currentDate) > 0){
					System.out.println(temp.get(0) + " " + temp.get(1));
					System.out.println(customerinfo);
					System.out.println("==========================================");
				}
				else if(date.compareTo(currentDate) == 0 && time.compareTo(currentTime) >= 0){
					System.out.println(temp.get(0) + " " + temp.get(1));
					System.out.println(customerinfo);
					System.out.println("==========================================");
				}
			}
			return true;
		}
		catch(NullPointerException e){
			System.out.println("No bookings in your database.\n");
			return false;
		}
		catch (ParseException e) {
			System.out.println("Invalid date format.\n");
			return false;
		}
	}


	/**
	 *	print all worker's name, email, contact number and working days/time in a week.
	 */
	public boolean viewWorkersAvailability(){
		try{
			ArrayList<ArrayList<String>> employee = databaseManager.getEmployee(username);
			System.out.println("Your emloyees' working days and time are as following:");
		 	System.out.println("======================================================");
			for(ArrayList<String> temp : employee){
				System.out.println(temp.get(0) + " " + temp.get(1));	//firstname + lastname
				System.out.println(temp.get(2));						//the emplyee's email
				System.out.println(temp.get(3));						//contact number
				System.out.println("Working Days/Time:");
				ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(temp.get(2));
				for(ArrayList<String> t : workingTime){
					System.out.println(t.get(0) + "   " + t.get(1));
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
	 * Get user's input for add business time from console.
	 */
	public void businessTimeInput(){
		try{
			int i;
			do{
				String business_day;
				String owner_username = username;
				String open_time;
				String closing_time;
				String week[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
				System.out.println("You have chosen option B: Add business time for the next month.");
				System.out.println("Please select business days in a week:");
				System.out.println("1.Monday  2.Tuesday  3.Wednesday  4.Thursday  5.Friday  6.Saturday  7.Sunday");
				business_day = week[Integer.parseInt(sc.nextLine()) -1];
				System.out.println("Please enter business open time with 24-hour format (HH:mm): ");
				open_time = sc.nextLine();
				System.out.println("Please enter business closing time with 24-hour format (HH:mm): ");
				closing_time = sc.nextLine();
				System.out.println("Please select one of the following options:");
				System.out.println("1 - Store the business time");
				System.out.println("2 - Quit without saving any information");
				i = Integer.parseInt(sc.nextLine());
				if(i == 1){
						addBusinessTime(business_day,owner_username,open_time,closing_time);
						return;
				}
			}while(i != 2);
			System.out.println("Information wasn't stored, return to the business menu.\n");
			return;
		}catch(Exception e){
			System.out.println("Invalid input!");
			return;
		}
	}


	/**
	 * Get user's input from console for adding employee.
	 */
	public void employeeInput(){
		try{
			int i;
			String firstname;
			String lastname;
			String email;
			String contactNumber;
			String workingDay;
			String workingTime;
			String owner_username = username;
			String week[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
			String period[] = {"9:00-13:00","13:00-17:00","9:00-13:00 and 13:00-17:00",""};

			System.out.println("You have chosen option A: Add a new employee.");
			System.out.println("Please enter the emplyee's first name:");
			firstname = sc.nextLine();
			System.out.println("Please enter the emplyee's last name:");
			lastname = sc.nextLine();
			System.out.println("Please enter the emplyee's e-mail address:");
			email = sc.nextLine();
			System.out.println("Please enter the emplyee's contact number:");
			contactNumber = sc.nextLine();
			do{
				System.out.println("Please select the emplyee's working day in a week:");
				System.out.println("1.Monday  2.Tuesday  3.Wednesday  4.Thursday  5.Friday  6.Saturday  7.Sunday");
				workingDay = week[Integer.parseInt(sc.nextLine()) -1];
				System.out.println("Please select the emplyee's working time in a day:");
				System.out.println("1. 9:00-13:00  2. 13:00-17:00  3. 9:00-13:00 and 13:00-17:00  4. null");
				workingTime = period[Integer.parseInt(sc.nextLine()) -1];
				System.out.println("Please select one of the following options:");
				System.out.println("1 - Store the employee information");
				System.out.println("2 - Add more working time");
				System.out.println("3 - Quit without saving any information");
				databaseManager.setWorkingTime(workingDay, workingTime, email);
				i = Integer.parseInt(sc.nextLine());
				if(i == 1){
					addEmployee(firstname,lastname,owner_username,email,contactNumber, workingDay, workingTime);
					return;
				}
			}while(i != 3);
			System.out.println("Employee information wasn't saved!");
			return;
		}
		catch(Exception e){
			System.out.println("Invalid input!");
			return;
		}
	}


	public void makeBooking() {
		// TODO Auto-generated method stub
		try{
			int j;
			do{
		String staff;
		String date;
		String time;
		String owner_username = username;
		String cust_username;
			ArrayList<ArrayList<String>> employee = databaseManager.getEmployee();
			System.out.println("Please select a staff:");
			for(int i=0; i<employee.size();i++){
				System.out.println(i+"."+employee.get(i));	//firstname + lastname	
			}
			staff = sc.nextLine();
			System.out.println("Please enter a date which you want booking");
			date = sc.nextLine();
			System.out.println("Please enter a time which you want booking");
			time = sc.nextLine();
			System.out.println("Please enter customer username");
			cust_username = sc.nextLine();
			System.out.println("Please select one of the following options:");
			System.out.println("1 - Store the business time");
			System.out.println("2 - Quit without saving any information");
			j = Integer.parseInt(sc.nextLine());
			if(j == 1){
					databaseManager.setBooking(date, time, staff, owner_username, cust_username);
					return;
			}
		}while(j != 2);
		System.out.println("Information wasn't stored, return to the business menu.\n");
		return;
	}catch(Exception e){
		System.out.println("Invalid input!");
		return;
	}
		}
	}

