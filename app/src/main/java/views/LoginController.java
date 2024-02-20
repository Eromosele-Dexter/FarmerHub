package views;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Customer;
import models.Farmer;
import models.User;
import repositories.userRepository.IUserRepository;
import repositories.userRepository.UserRepository;


public class LoginController {
	
	private IUserRepository	userRepository;

	public LoginController() {
		this.userRepository = new UserRepository();
	}


	protected EventHandler<ActionEvent> onLoginButtonClick(Text actionTarget, TextField userTextField,
			PasswordField pwBox, Stage stage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String username = userTextField.getText();

				String password = pwBox.getText();

				User user = userRepository.validateUser(username, password);

				boolean isValidUser = user != null;


				if (isValidUser){
					actionTarget.setFill(javafx.scene.paint.Color.GREEN);
					actionTarget.setText("Login successful");

					if(user instanceof Farmer) {
						FarmerLandingPage farmerLandingPage = new FarmerLandingPage(stage);
						return;
					}
					else if (user instanceof Customer) {
						CustomerLandingPage customerLandingPage = new CustomerLandingPage(stage);
						return;
					}

				}	
				actionTarget.setText("Login failed");
			}
		};
	}
	
	protected ChangeListener<? super String> onUserNameTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}
	protected ChangeListener<? super String> onPasswordTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}

	// Event Handler for Register Button
	protected EventHandler<ActionEvent> onRegisterButtonClick(Stage stage) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 RegistrationPageView registrationPage = new RegistrationPageView(stage);
			}

		};

	}

}
