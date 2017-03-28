package app;

import controller.ViewController;
import model.ClientModel;
import user.BusinessOwner;
import user.Customer;

/**
 * This is the main class for the system. All data will be initiated including business owner, customer, database.
 * And start the main menu.
 * @author ranlu
 *
 */
public class AppointmentBookingSystem {

	public static void main(String[] args) throws Exception{

		ViewController view = new ViewController();
		ClientModel clientModel = new ClientModel();
		BusinessOwner businessOwner = new BusinessOwner();
		Customer customer = new Customer();

		clientModel.initDatabase();
		clientModel.initViewController(view);
		clientModel.initBusinessOwner(businessOwner);
		clientModel.initCustomer(customer);
		clientModel.initMenu();
	}
}