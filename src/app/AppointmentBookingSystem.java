package app;

import controller.ViewController;
import model.ClientModel;

public class AppointmentBookingSystem {

	public static void main(String[] args) throws Exception{

		ViewController view = new ViewController();
		ClientModel model = new ClientModel();
		view.initMenu();
		model.options();

	}

}
