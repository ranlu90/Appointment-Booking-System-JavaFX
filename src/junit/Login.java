package junit;

import org.junit.Test;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import systemController.LoginController;

public class Login {
	
	private LoginController loginController = new LoginController();
	private TextField username = new TextField();
	private PasswordField password = new PasswordField();
	
	@Test
	public void test() {
	    username.setText("owner");
	    password.setText("owner");
	    loginController.setUsername(username);
	    loginController.setPassword(password);
		assert(loginController.login());
	}

}
