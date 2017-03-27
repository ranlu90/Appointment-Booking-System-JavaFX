package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ViewController {

	private static final Scanner sc = new Scanner(System.in);
	private BusinessController businessController = new BusinessController();

	public ViewController(){}


	/**
	 * Initiate system main menu, allow user to enter options.
	 */
	public void initMenu(){

	}


	/**
	 * Add logging information to console.
	 * @param from the class name for printed information.
	 * @param message print to console.
	 */
    public void add(String from, String message)
    {
        // Get the date and format it for output
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        // Create the output message
        String logMsg = "["+dateFormat.format(date)+"] "+from+": "+message;
        // Output to console
        System.out.println(logMsg);
    }


	/**
	 * Go to main menu of business owner, functions include
	 * add a new employee, add working time/dates for the next month,
	 * look at the summaries of bookings, new booking,
	 * show all workers’ availability for the next 7 days.
	 */
	public void gotoBusiness()
	{
		 String input;
	        char selection = '\0';
	        do
	        {
		System.out.println("A.Add a new employee");
		System.out.println("B.Add working time/dates for the next month");
		System.out.println("C.View the summaries of bookings");
		System.out.println("D.View new booking");
		System.out.println("E.Show all workers’ availability for the next 7 days");
		System.out.println("X.Logout");
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
            	businessController.addEmployee();
                break;

            case 'B':
            	businessController.addBusinessHours();
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
		String input2;
		char selection2 = '\0';

		do
		{
			System.out.println("A.View availabe days/time");
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
