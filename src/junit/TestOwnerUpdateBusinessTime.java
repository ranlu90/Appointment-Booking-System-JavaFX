package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseManager;
import systemController.ViewController;

public class TestOwnerUpdateBusinessTime {

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
		assertTrue(databaseManager.setBusinessTime("Sunday", "owner", "10:00", "15:00"));
	}
	
	@Test
	public void test2() {
		assertTrue(databaseManager.updateBusinessHours("Sunday", "owner", "10:00", "15:00"));
	}
}
