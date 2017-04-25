package controller;


import java.util.ArrayList;
import java.util.Scanner;

import database.DatabaseManager;

/**
 * Include functions for customer, provide fundamental methods for viewController.
 * Parameters received from ViewController class, managing information in customer.
 * @author ranlu
 */
public class CustomerController {

	private String username;
	private DatabaseManager databaseManager;
	private static Scanner sc = new Scanner(System.in);
	public CustomerController(){}
    

	/**
	 * set database for business owner
	 * @param database get from viewController
	 */
	public void setDatabaseManager(DatabaseManager database){
		this.databaseManager = database;
	}

	public void setUserName(String username) {
		this.username = username;
	}
    
	/**
	 * Customer can view available booking days and time.
	 */
	public void viewBookingAvailability(){
		System.out.println("Available booking dates and time for 'owner' are:");
		System.out.println("business_date  open_time   closing_time");
		System.out.println("-------------  ----------  ------------");
		ArrayList<ArrayList<String>> businessTime = databaseManager.getBusinessTime("owner");
		for(ArrayList<String> temp : businessTime){
			System.out.println(temp.get(0) + "          " + temp.get(1) + "         "  + temp.get(2));
		}
	}


	public void makeBooking() {
		try{
			int j;
			do{
		String staff;
		String date;
		String time;
		String owner_username = "owner";
		String cust_username = username;
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


