package junit;

import static org.junit.Assert.*;
import org.junit.Test;
import model.ClientModel;

public class TestLogin {
	private static ClientModel clientModel = new ClientModel();

	@Test
	public void test1() {
		//check both username and password exists in business
		boolean f = clientModel.login("admin","admin");
		assertTrue(f);
	}

	@Test
	public void test2() {
		//check username exists, password matches another username in the business
		boolean f = clientModel.login("admin","toniguy");
		assertFalse(f);
	}

	@Test
	public void test3() {
		//check both username and password don't exist in the business
		boolean f = clientModel.login("wrong","wrong");
		assertFalse(f);
	}

	@Test
	public void test4() {
		//check both username and password exists in customerinfo
		boolean f = clientModel.login("customer","customer");
		assertTrue(f);
	}

	@Test
	public void test5() {
		//check username exists, password matches another username in customerinfo
		boolean f = clientModel.login("customer","david");
		assertFalse(f);
	}

	@Test
	public void test6() {
		//check both username and password don't exist in customerinfo
		boolean f = clientModel.login("wrong","wrong");
		assertFalse(f);
	}

	@Test
	public void test7() {
		//check both username and password are null
		boolean f = clientModel.login("","");
		assertFalse(f);
	}
}
