package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BusinessController;

public class AddBusinessHoursTest {

	@Test
	public void test() {
		BusinessController business = new BusinessController();
		boolean f = business.addBusinessHours();
		assertTrue(f);
	}

}
