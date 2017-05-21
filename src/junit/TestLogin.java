package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import database.DatabaseManager;
import systemController.ViewController;


public class TestLogin {
	
	private static ViewController viewController;
	private static DatabaseManager databaseManager;
	private String username,password;
	
	@BeforeClass
	public static void setupDatabase() throws IOException { 
		viewController = new ViewController();
		databaseManager = new DatabaseManager();
		viewController.initDatabase(databaseManager);
	}
	
	@Test
	public void test1() {
	    username = "owner";
	    password = "owner";
		assertTrue(databaseManager.searchBusiness(username, password));
	}
	
	@Test
	public void test2() {
	    username = "customer";
	    password = "customer";
		assertTrue(databaseManager.searchCustomer(username, password));
	}
	
	@Test
	public void test3() {
	    username = "owner";
	    password = "customer";
		assertFalse(databaseManager.searchBusiness(username, password));
	}
	
	@Test
	public void test4() {
	    username = "abc";
	    password = "def";
	    assertFalse(databaseManager.searchBusiness(username, password));
	}
}
