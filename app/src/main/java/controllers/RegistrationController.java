package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import repositories.userRepository.UserRepository;
import services.UserService;
import views.LoginView;

public class RegistrationController {

	// Event Handler for Back to Login Button
		public static EventHandler<ActionEvent> onBackToLoginButtonClick(Stage stage) {
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
		public static EventHandler<ActionEvent> onRegisterButtonClick(TextField fnTextField, TextField lnTextField, TextField unTextField, PasswordField pwBox, ChoiceBox<String> cb, Text actionTarget, Stage stage) {

			return new EventHandler<ActionEvent>() {

				UserService userService = new UserService(new UserRepository());

				@Override
				public void handle(ActionEvent event) {
					String firstName = fnTextField.getText();
					String lastName = lnTextField.getText();
					String userName = unTextField.getText();
					String password = pwBox.getText();
					String role = cb.getValue();

					userService.handleRegisterUser(firstName, lastName, userName, password, role, actionTarget);

					PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

					delay.setOnFinished(e -> {
						LoginView loginView = new LoginView(new LoginController());
						loginView.start(stage);
					});

					delay.play();
				}       
			};

		}


		
}
