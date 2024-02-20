package views;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Customer;
import models.Farmer;
import models.User;
import repositories.userRepository.IUserRepository;
import repositories.userRepository.UserRepository;
import statics.UserRoles;

public class RegistrationController {

	// Event Handler for Back to Login Button
		protected static EventHandler<ActionEvent> onBackToLoginButtonClick(Stage stage) {
			return new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Go Back to Login Page
					LoginView loginView = new LoginView(new LoginController());
		            loginView.start(stage);
		
				}

			};

		}
		
		// Event Handler for Register Button
		protected static EventHandler<ActionEvent> onRegisterButtonClick(TextField fnTextField, TextField lnTextField, TextField unTextField, PasswordField pwBox, ChoiceBox<String> cb, Text actionTarget, Stage stage) {
			return new EventHandler<ActionEvent>() {

				IUserRepository userRepository = new UserRepository();

				@Override
				public void handle(ActionEvent event) {
					String firstName = fnTextField.getText();
					String lastName = lnTextField.getText();
					String userName = unTextField.getText();
					String password = pwBox.getText();
					String role = cb.getValue();

					String validationMessage = validateUserInput(firstName, lastName, userName, password, role);

					if (!validationMessage.isEmpty()) {

						actionTarget.setText(validationMessage);

						return; 
					}
		

					User newUser = null;

					if(role.toUpperCase().equals(UserRoles.FARMER)) {
						newUser = new Farmer(firstName, lastName, userName, password);
					}
					else {
						newUser = new Customer(firstName, lastName, userName, password);
					}
		
					new User(firstName, lastName, userName, password);

					User user = userRepository.getUserByUsername(userName);

					if (user != null) {
						actionTarget.setText("User already exists");
						return;
					}

					userRepository.createUser(newUser);

					actionTarget.setText("Registration Successful");
					
					PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

					delay.setOnFinished(e -> {
						LoginView loginView = new LoginView(new LoginController());
						loginView.start(stage);
					});

					delay.play();
				}       
			};
		}

		private static String validateUserInput(String firstName, String lastName, String userName, String password, String role) {
			StringBuilder missingFields = new StringBuilder("Missing fields: ");

			if (firstName == null || firstName.trim().isEmpty()) missingFields.append("\nFirst Name, ");

			if (lastName == null || lastName.trim().isEmpty()) missingFields.append("\nLast Name, ");

			if (userName == null || userName.trim().isEmpty()) missingFields.append("\nUser Name, ");

			if (password == null || password.trim().isEmpty()) missingFields.append("\nPassword, ");

			if (role == null || role.trim().isEmpty()) missingFields.append("\nRole, ");
			

			if (missingFields.length() > "Missing fields: ".length()) {

				missingFields.setLength(missingFields.length() - 2);

				return missingFields.toString();

			} else {
				// Return empty string if no fields are missing
				return "";
			}
		}

		
}
