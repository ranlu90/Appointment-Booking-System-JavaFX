package junit;


import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import customerController.CustomerBookingAvailabilityController;
import database.DatabaseManager;
import systemController.ViewController;

public class TestCustomerBookingAvailability {
	
	private static ViewController viewController;
	private static DatabaseManager databaseManager;
	private static CustomerBookingAvailabilityController customer;
	
	@BeforeClass
	public static void setupDatabase() throws IOException { 
		viewController = new ViewController();
		databaseManager = new DatabaseManager();
		viewController.initDatabase(databaseManager);
		customer = new CustomerBookingAvailabilityController();
	}
	
	
	@Test
	public void test() {
		assert(customer.CheckTimeSlot("Andrew@gmail.com", "Tudesday", "2017-06-06")) != null;
	}

}
