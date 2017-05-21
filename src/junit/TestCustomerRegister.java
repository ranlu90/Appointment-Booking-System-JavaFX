package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseManager;
import systemController.ViewController;

public class TestCustomerRegister {
	private static ViewController viewController;
	private static DatabaseManager databaseManager;
	
	@BeforeClass
	public static void setupDatabase() throws IOException { 
		viewController = new ViewController();
		databaseManager = new DatabaseManager();
		viewController.initDatabase(databaseManager);
	}

	@Test
	public void test1() {
		assertTrue(databaseManager.insertIntoCustomer("Test", "Ryan", "Melbourne", "0123456789", "testCustomer", "testCustomer"));
	}
}
