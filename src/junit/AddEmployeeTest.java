package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BusinessController;

public class AddEmployeeTest {

	@Test
	public void test() {
		BusinessController business = new BusinessController();
		boolean f = business.addEmployee("David", "Short", "owner", "david.short@nottingham.ac.uk", "123 456", "Monday", "9:00-11:00");
		assertTrue(f);
	}

}
