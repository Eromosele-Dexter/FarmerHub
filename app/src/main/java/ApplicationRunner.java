
import javafx.stage.Stage;
import views.LoginController;
import views.LoginView;

import java.io.IOException;

import database.StubDB;

public class ApplicationRunner extends javafx.application.Application {
	static LoginController loginController = new LoginController();
	@Override
	public void start(Stage stage) throws IOException {
		LoginView login = new LoginView(loginController);
		// Initialize the User Database
		StubDB.intializeUserDB();
		// Start the Login View
		login.start(stage);
	}
	public static void main(String[] args) {
		launch();
        System.out.println("ApplicationRunner.java Running....");
	}
}