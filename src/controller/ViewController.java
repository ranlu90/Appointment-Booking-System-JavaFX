package controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import database.DatabaseManager;

/**
 * This class manages functions of add logging information, gotoBusiness and gotoCustonmer.
 * parameters received from clientModel, allow the user to select functions in business or customer menu,
 * pass the user's selection to BusinessController or CustomerController.
 * @author ranlu
 */
public class ViewController {


	private BusinessController businessController = new BusinessController();
	private CustomerController customerController = new CustomerController();
	private static Scanner sc = new Scanner(System.in);
	private String username;
	private DatabaseManager databaseManager;

	public ViewController(){}


	public void setUserName(String username){
		this.username = username;
	}


	/**
	 * set database for view controller
	 * @param database get from clientModel
	 */
	public void setDatabaseManager(DatabaseManager databaseManager){
		this.databaseManager = databaseManager;
	}


	/**
	 * Add logging information to console.
	 * @param from - the class name for printed information.
	 * @param message - print to console.
	 */
    public void add(String from, String message)
    {
        // Get the date and format it for output
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        // Create the output message
        String logMsg = "[" + dateFormat.format(date) + "] "+ from +": "+ message;
        // Output to console
        System.out.println(logMsg);
    }


	/**
	 * Go to main menu of business owner, functions include
	 * add a new employee, add working time/dates for the next month,
	 * look at the summaries of bookings, new booking,
	 * show all workers’ availability for the next 7 days.
	 * @throws
	 */
	public void gotoBusiness()
	{
		 businessController.setUsername(username);
		 businessController.setDatabaseManager(databaseManager);
		 String input;
		 char selection = '\0';
		 do
		 {
			 System.out.println("Please select one of the following options:");
			 System.out.println("A - Add a new employee");
			 System.out.println("B - Add working time/dates for the next month");
			 System.out.println("C - View the summaries of bookings");
			 System.out.println("D - View new bookings");
			 System.out.println("E - Show all workers’ availability for the next 7 days");
			 System.out.println("X - Logout");
			 System.out.print("Enter your selection: ");
			 input = sc.nextLine();

			 System.out.println();

        if (input.length() != 1){
            System.out.println("Error - selection must be a single character!");
        }
        else{
            // extract the user's menu selection as a char value and
            // convert it to upper case so that the menu becomes
            // case-insensitive
            selection = Character.toUpperCase(input.charAt(0));

            // process the user's selection
            switch (selection)
            {
            case 'A':
            	businessController.employeeInput();
                break;

            case 'B':
            	businessController.businessTimeInput();
                break;

            case 'C':
            	businessController.viewAllBookings();
                break;

            case 'D':
            	businessController.viewNewBookings();
                break;

            case 'E':
            	businessController.viewWorkersAvailability();
                break;

            case 'X':
                break;

            default:
                // default case - handles invalid selections
                System.out.println("Error - invalid selection!");
            }
        }
        System.out.println();
		 } while (selection != 'X');
	}


	/**
	 * Go to main menu of customer, customer can only view available days/time.
	 */
	public void gotoCustomer()
	{
		customerController.setUserName(username);
		customerController.setDatabaseManager(databaseManager);
		String input2;
		char selection2 = '\0';

		do
		{
		    System.out.println("Please select one of the following options:");
			System.out.println("A - View available days/time");
			System.out.println("X - Logout");
			System.out.print("Enter your selection: ");
			input2 = sc.nextLine();

			System.out.println();

			if (input2.length() != 1)
			{
				System.out.println("Error - selection must be a single character!");

			}
			else
			{
            // extract the user's menu selection as a char value and
            // convert it to upper case so that the menu becomes
            // case-insensitive

            selection2 = Character.toUpperCase(input2.charAt(0));

            // process the user's selection
            switch (selection2)
            {
            case 'A':
            	//view available days/time
            	customerController.viewBookingAvailability();
            	break;

            case 'X':
                break;

            default:
                // default case - handles invalid selections
                System.out.println("Error - invalid selection!");
            }
        	}
        	System.out.println();
    	} while (selection2 != 'X');
	}
}
