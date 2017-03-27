package app;

import java.util.Scanner;
import model.ClientModel;
import user.BusinessOwner;
import user.Customer;

public class AppointmentBookingSystem {

	private static final Scanner sc = new Scanner(System.in);
	private static ClientModel clientModel = new ClientModel();

	public static void main(String[] args) throws Exception{

		BusinessOwner businessOwner = new BusinessOwner();
		Customer customer = new Customer();

		clientModel.initDatabase();
		clientModel.initBusinessOwner(businessOwner);
		clientModel.initCustomer(customer);

		 String input;
		 String username;
		 String password;
		 String firstname;
		 String lastname;
		 String address;
		 String contactNumber;

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
	            		//get user input
	            		System.out.print("Username: ");
	            		username = sc.nextLine();
	            		System.out.print("Password: ");
	            		password = sc.nextLine();
	                    clientModel.login(username,password);
	                    break;

	                case 'B':
	            		//regular expression for password check
	            		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
	            		//get user input
	            		System.out.print("Username: ");
	            		username = sc.nextLine();
	            		//^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{1,}$
	            		if(!username.matches("^[0-9a-zA-Z@\\.]{1,}$")){
	            			System.out.println("Username only contains digits, letters and @ or dot without any white spaces!");
	            			break;
	            		}
	            		System.out.println("Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.");
	            		System.out.print("Password: ");
	            		password = sc.nextLine();
	            		if(!password.matches(check)){
	            			System.out.println("Password is too easy, return to the main menu.");
	            			System.out.println("=========================================");
	            			break;
	            		}
	            		System.out.print("Confirm Password: ");
	            		String password2 = sc.nextLine();
	            		if(password.matches(password2)){
	        				System.out.print("Please enter your first name: ");
	        				firstname = sc.nextLine();
	        				System.out.print("Please enter your last name: ");
	        				lastname = sc.nextLine();
	        				System.out.print("Please enter your address: ");
	        				address = sc.nextLine();
	        				System.out.print("Please enter your contact number: ");
	        				contactNumber = sc.nextLine();

	        				clientModel.register(firstname,lastname,address,contactNumber,username,password);
	            		}
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