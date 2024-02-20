
import javafx.stage.Stage;
import utils.DatabaseSeedingUtil;
import views.LoginView;

import java.io.IOException;

import controllers.LoginController;
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
		System.out.println("ApplicationRunner.java Running....");
		DatabaseSeedingUtil db = new DatabaseSeedingUtil();
		db.seedDatabase();
	}
	public static void main(String[] args) {

		launch();
	}
}