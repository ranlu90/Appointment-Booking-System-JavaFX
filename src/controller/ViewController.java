package controller;

public class ViewController {

	public ViewController()
	{

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
