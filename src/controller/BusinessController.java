package controller;

import java.util.Scanner;
import business.Employee;
import user.BusinessOwner;

/**
 * Include functions for business owner, provide fundamental methods for viewController, add information to business owner.
 * Parameters received from ViewController class, managing information in business owner.
 * @author ranlu
 *
 */
public class BusinessController {


	private BusinessOwner businessOwner = new BusinessOwner();
	private static Scanner sc = new Scanner(System.in);


	public BusinessController(){}

	/**
	 * from each business owner, add employee information
	 * @return true if adding info successfully
	 */
	public boolean addEmployee(){

		String selection;

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
			employee.setEmail(email);
			System.out.println("Please enter the emplyee's contact number:");
			contactNumber = sc.nextLine();
			employee.setContactNumber(contactNumber);
			do{
				System.out.println("Please enter the emplyee's working day in a week:");
				workingDay = sc.nextLine();
				System.out.println("Please enter the emplyee's working time:");
				workingTime = sc.nextLine();
				employee.getWorkingSchedule().put(workingDay, workingTime);
				System.out.println("Enter anything to add more working days/time OR");
				System.out.println("Enter 'save' to store the employee's details:");
				selection = sc.nextLine();
			}while(!selection.equalsIgnoreCase("save"));
			businessOwner.getEmployeeList().add(employee);
			System.out.println("The employee's details have been added to your employee list.\n");
			return true;
		}
		else{
			System.out.println("email address format is invalid!");
			return false;
		}
	}


	/**
	 *
	 */
	public boolean addBusinessHours(){
		return false;
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
		if(!businessOwner.getEmployeeList().isEmpty()){
			System.out.println("Your emloyees' working days and time are as following:");
			for(Employee employee : businessOwner.getEmployeeList()){
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
		else{
			System.out.println("No employees in your employee list.\n");
			return false;
		}
	}
}
