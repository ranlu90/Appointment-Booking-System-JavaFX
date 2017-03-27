package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ClientModel;

public class RegisterTest2 {

	private static ClientModel clientModel = new ClientModel();

	@Test
	public void test() {
		//check username already exists in the database
		boolean f = clientModel.register("test","test","test","test","customer","customer");
		assertFalse(f);
	}

}
