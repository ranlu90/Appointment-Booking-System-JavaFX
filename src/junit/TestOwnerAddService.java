package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseManager;
import systemController.ViewController;

public class TestOwnerAddService {

	private static ViewController viewController;
	private static DatabaseManager databaseManager;
	
	@BeforeClass
	public static void setupDatabase() throws IOException { 
		viewController = new ViewController();
		databaseManager = new DatabaseManager();
		viewController.initDatabase(databaseManager);
	}
	
	@Test
	public void test() {
		assertTrue(databaseManager.addService("test service", "60", "owner", ""));
	}

}
