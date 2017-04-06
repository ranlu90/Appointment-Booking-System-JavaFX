package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BusinessController;

public class AddBusinessTimeTest {

	@Test
	public void test() {
		BusinessController business = new BusinessController();
		boolean f = business.addBusinessTime("abc", "owner", "11:00", "16:00");
		assertFalse(f);
	}

}
