package app;

import java.util.Scanner;
import model.ClientModel;

public class AppointmentBookingSystem {

	private static final Scanner sc = new Scanner(System.in);
	private static ClientModel clientModel = new ClientModel();

	public static void main(String[] args) throws Exception{

		clientModel.initDatabase();



		 String input;
	        char selection = '\0';
	        do
	        {
	            // display menu options
	            System.out.println("Welcome to Appointment Booking System!");
	            System.out.println("Please select one of the following options:");
	            System.out.println("A - Login");
	            System.out.println("B - Register");
	            System.out.println("X - Exit Program");

	            // prompt the user to enter their selection
	            System.out.print("Enter your selection: ");
	            input = sc.nextLine();

	            System.out.println();

	            if (input.length() != 1)
	            {
	                System.out
	                        .println("Error - selection must be a single character!");

	            } else
	            {
	                // extract the user's menu selection as a char value and
	                // convert it to upper case so that the menu becomes
	                // case-insensitive

	                selection = Character.toUpperCase(input.charAt(0));

	                // process the user's selection
	                switch (selection)
	                {
	                case 'A':

	                    // call addTour() helper method
	                    clientModel.login();
	                    break;

	                case 'B':

	                    // call displayTourSummary() helper method
	                   clientModel.register();
	                    break;

	                case 'X':

	                    System.out.println("Booking system shutting down goodbye!");
	                    break;

	                default:

	                    // default case - handles invalid selections
	                    System.out.println("Error - invalid selection!");

	                }
	            }
	            System.out.println();

	        } while (selection != 'X');




}

}