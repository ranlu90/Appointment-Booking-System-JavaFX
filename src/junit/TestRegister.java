package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ClientModel;

public class TestRegister {
	private static ClientModel clientModel = new ClientModel();

	@Test
	public void test1() {
		//check username already exists in the database
		boolean f = clientModel.register("test1","test1","test1","test1","admin","admin");
		assertFalse(f);
	}

	@Test
	public void test2() {
		//check firstname is null
		boolean f = clientModel.register("","test2","test2","test2","test2","Test200");
		assertFalse(f);
	}

	@Test
	public void test3() {
		//check lastname is null
		boolean f = clientModel.register("test3","","test3","test3","test3","Test300");
		assertFalse(f);
	}

	@Test
	public void test4() {
		//check password is invalid
		boolean f = clientModel.register("test4","test4","test4","test4","test4","test4");
		assertFalse(f);
	}

	@Test
	public void test5() {
		//check null values for address and contact number
		boolean f = clientModel.register("test5","test5","","","test5","Test500");
		assertTrue(f);
	}

	@Test
	public void test6() {
		//check every attribute meets the requirements
		boolean f = clientModel.register("test6","test6","","","test6","Test600");
		assertTrue(f);
	}
}
