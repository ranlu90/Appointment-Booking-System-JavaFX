package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ClientModel;

public class TestLogin {

	@Test
	public void test1() {
		ClientModel clientModel = new ClientModel();
		boolean f = clientModel.login();
		assertTrue(f);
	}
}
