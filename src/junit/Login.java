package junit;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import systemController.LoginController;

public class Login {
	private LoginController loginController;
	private TextField username;
	private PasswordField password;
	@Before
	public void setupQuery() { 
		LoginController loginController = new LoginController();
		TextField username = new TextField();
		PasswordField password = new PasswordField();
	}
	@Test
	public void test() {
	    username.setText("owner");
	    password.setText("owner");
	    loginController.setUsername(username);
	    loginController.setPassword(password);
		assert(loginController.login());
	}

}
