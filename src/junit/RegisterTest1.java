package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ClientModel;

public class RegisterTest1 {

		private static ClientModel clientModel = new ClientModel();

		@Test
		public void test2() {
			//check every attribute meets the requirements
			boolean f = clientModel.register("test","test","test","test","test","Test000");
			assertTrue(f);
		}


}
