package views.customerViews;

import java.util.List;

import controllers.ItemController;
import controllers.OrderController;
import controllers.ReviewController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.Machine;
import models.User;
import utils.StringUtils;

public class CustomerLandingPage {


	public CustomerLandingPage(Stage stage, User user) {
		int userId = user.getId();


		HBox topBar = new HBox();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(15));
		topBar.setSpacing(10);

		Label greeting = new Label("Hi " + user.getFirstName() + "! 👋");
		greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

		HBox linksContainer = new HBox(5); 
		linksContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS); 

		Hyperlink orderHistoryLink = new Hyperlink("Order History 📜");

		orderHistoryLink.setStyle("-fx-font-size: 16px;");

		Hyperlink goToCartLink = new Hyperlink("Cart 🛒");
		
		
		goToCartLink.setStyle("-fx-font-size: 16px;");

		linksContainer.getChildren().addAll(orderHistoryLink, goToCartLink);

		topBar.getChildren().addAll(greeting, linksContainer);

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
			Label itemPrice = new Label(String.format("Price: $%s", StringUtils.formatNumberPrice(item.getPrice())));
			Label itemQuantity = new Label("Quantity Available: " + item.getQuantityAvailable());

			Label itemCondition = null;
			if (item instanceof Machine) {
				Machine machine = (Machine) item;
				itemCondition = new Label("Condition: " + StringUtils.capitalize(machine.getCondition()).replace("_", " "));
			}

			// Quantity adjustment controls
			Label quantityLabel = new Label("0"); // Start with a default quantity
			Button decreaseButton = new Button("-");
			Button increaseButton = new Button("+");
			HBox quantityAdjustmentBox = new HBox(5, decreaseButton, quantityLabel, increaseButton);
			quantityAdjustmentBox.setAlignment(Pos.CENTER_LEFT);

			decreaseButton.setOnAction(e -> {
				int quantity = Integer.parseInt(quantityLabel.getText());
				if (quantity > 1) { // Prevent quantity from going below 1
					quantityLabel.setText(String.valueOf(--quantity));
				}
			});

			increaseButton.setOnAction(e -> {
				int quantity = Integer.parseInt(quantityLabel.getText());
				if(quantity < item.getQuantityAvailable()) {
					quantityLabel.setText(String.valueOf(++quantity)); 
				}
			});

			Button addToCartButton = new Button("Add to Cart");
			Button seeReviewsButton = new Button("See Reviews");
			HBox buttonsBox = new HBox(10, addToCartButton, seeReviewsButton);
			buttonsBox.setAlignment(Pos.CENTER_RIGHT);

			addToCartButton.setOnAction(e -> {
				int quantity = Integer.parseInt(quantityLabel.getText()); // Get the adjusted quantity
				OrderController.addToCart(item, quantity, userId);
				int cartSize = OrderController.getTotalCartQuantity(userId);
				goToCartLink.setText("Cart" + "("+cartSize+") "+ "🛒");
			});

			seeReviewsButton.setOnAction(e -> {
				Scene currentScene = stage.getScene();
				ReviewController.viewReviews(item, stage, currentScene, userId);
			});

			HBox bottomBox = new HBox();
			bottomBox.setSpacing(10);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS); 
			bottomBox.getChildren().addAll(quantityAdjustmentBox, spacer, buttonsBox);


			card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);
			if (itemCondition != null) {
				card.getChildren().add(itemCondition);
			}

			card.getChildren().add(bottomBox); 
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
		orderHistoryLink.setOnAction(e -> showOrderHistoryPage(stage, userId, scene));
		goToCartLink.setOnAction(e -> showCartPage(stage, userId, scene));
        stage.setScene(scene);
        stage.show();
	}

	private void showOrderHistoryPage(Stage stage, int userId, Scene scene) {
		new OrderHistoryPage(stage, userId, scene);
	}

	private void showCartPage(Stage stage, int userId, Scene scene) {
		new CartPage(stage, userId, scene);
	}
}