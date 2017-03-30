package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BusinessController;

public class AddEmployeeTest {

	@Test
	public void test() {
		BusinessController business = new BusinessController();
		boolean f = business.addEmployee();
		assertTrue(f);
	}

}
