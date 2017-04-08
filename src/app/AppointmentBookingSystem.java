package app;

import model.ClientModel;


/**
 * This is the main class for the system. All data will be initiated including clientModel and database
 * and launch the main menu.
 * @author ranlu
 *
 */
public class AppointmentBookingSystem {

	public static void main(String[] args) throws Exception{

		ClientModel clientModel = new ClientModel();

		clientModel.initDatabase();
		clientModel.initMenu();
	}
}