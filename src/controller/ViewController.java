package controller;

import java.util.Scanner;

public class ViewController {

	public ViewController()
	{

	}
	public void initMenu(){
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Appointment Booking System!");
		System.out.println("Please select one of the following options:");
		System.out.println("1:Business Owner Login");
		System.out.println("2:Customer Login");
		System.out.println("3:Customer Registration");
		System.out.print("Your selection is:");
		int input = sc.nextInt();

		if(input == 1){
			businessLogin();
		}
		else if(input == 2){
			customerLogin();
		}
		else if(input == 3){
			register();
		}
		else{
			System.out.println("Invaild selection!");
			return;
		}
	}
	/**
	 * Go to main menu of business owner, functions include
	 * add a new employee, add working time/dates for the next month,
	 * look at the summaries of bookings, new booking,
	 * show all workers’ availability for the next 7 days.
	 */
	public void gotoBusiness()
	{
		System.out.println("1.Add a new employee");
		System.out.println("2.Add working time/dates for the next month");
		System.out.println("3.View the summaries of bookings");
		System.out.println("4.View new booking");
		System.out.println("5.Show all workers’ availability for the next 7 days");
	}
	/**
	 * Go to main menu of customer, customer can only view available days/time.
	 */
	public void gotoCustomer()
	{
		System.out.println("1.View availabe days/time");
	}
}
