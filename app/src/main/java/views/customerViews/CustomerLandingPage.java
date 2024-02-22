package views.customerViews;

import java.util.List;

import controllers.ItemController;
import controllers.OrderController;
import controllers.ReviewController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;
import models.Machine;
import models.User;

public class CustomerLandingPage {
	public CustomerLandingPage(Stage stage, User user) {
		int userId = user.getId();
		stage.setTitle("Customer Landing Page");

		// Top bar for greeting, order history, and cart link
		HBox topBar = new HBox();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(15));
		topBar.setSpacing(10);

		Label greeting = new Label("Hi " + user.getFirstName() + "! 👋");
		greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

		// Links container to group Order History and Cart, aligned at the top right
		HBox linksContainer = new HBox(5); // Spacing of 5px between links
		linksContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS); // This will push the linksContainer to the right

		Hyperlink orderHistoryLink = new Hyperlink("Order History 📜");
		orderHistoryLink.setOnAction(e -> showOrderHistoryPage(stage, userId));
		orderHistoryLink.setStyle("-fx-font-size: 16px;");

		Hyperlink goToCartLink = new Hyperlink("Cart 🛒");
		goToCartLink.setOnAction(e -> showCartPage(stage, userId));
		goToCartLink.setStyle("-fx-font-size: 16px;");

		// Add both links to the links container
		linksContainer.getChildren().addAll(orderHistoryLink, goToCartLink);

		// Add greeting and links container to the top bar
		topBar.getChildren().addAll(greeting, linksContainer);

		// Main container VBox inside the ScrollPane
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);

		List<Item> fetchedItems = ItemController.handleGetAllItems();
        List<Item> items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems) : FXCollections.observableArrayList();

		for (Item item : items) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-border-color: lightgrey; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f9f9f9;");

            Label itemName = new Label("Name: " + item.getName());
            itemName.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");
            Label itemDescription = new Label("Description: " + item.getDescription());
            Label itemPrice = new Label("Price: $" + item.getPrice());
            Label itemQuantity = new Label("Quantity Available: " + item.getQuantityAvailable());

            Label itemCondition = null;
            if (item instanceof Machine) {
                Machine machine = (Machine) item;
                itemCondition = new Label("Condition: " + machine.getCondition());
            }

            Button addToCartButton = new Button("Add to Cart");
            Button seeReviewsButton = new Button("See Reviews");

            HBox buttonsBox = new HBox(10);
            buttonsBox.setAlignment(Pos.CENTER_RIGHT);
            buttonsBox.getChildren().addAll(addToCartButton, seeReviewsButton);

			int quantity = 5;

            addToCartButton.setOnAction(e -> {
				OrderController.addToCart(item, quantity, userId);
			});

            seeReviewsButton.setOnAction(e -> {
                ReviewController.viewReviews(item, stage);
            });

            card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);

            if (itemCondition != null) {
                card.getChildren().add(itemCondition);
            }

            card.getChildren().add(buttonsBox);
            vbox.getChildren().add(card);
        }


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true); 
        scrollPane.setPadding(new Insets(10));

		BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.show();
	}

	private void showOrderHistoryPage(Stage stage, int userId) {
		new OrderHistoryPage(stage, userId);
	}

	private void showCartPage(Stage stage, int userId) {
		new CartPage(stage, userId);
	}
}