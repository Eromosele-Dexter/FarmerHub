package views.customerViews;

import java.util.List;

import controllers.LoginController;
import controllers.OrderController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.composite_responses.OrderItemResponse;
import views.LoginView;


public class CartPage {
    public CartPage(Stage stage, int userId, Scene previousScene) {
        stage.setTitle("Cart Overview");

        // Top bar for back button and page title
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(previousScene));
    
        Label pageTitle = new Label("Your Cart");
        pageTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    
        HBox.setHgrow(pageTitle, Priority.ALWAYS);
        pageTitle.setMaxWidth(Double.MAX_VALUE);
        pageTitle.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(backButton, pageTitle);
    
        List<OrderItemResponse> cartItems = OrderController.viewCart(userId);
    
        VBox itemsContainer = new VBox(10);
        itemsContainer.setAlignment(Pos.TOP_CENTER);
        itemsContainer.setSpacing(5);
        itemsContainer.setPadding(new Insets(10));

    double total = 0;
    int totalQuantity = 0;

    if (cartItems.isEmpty()) {
        Label emptyCartLabel = new Label("Add items to Your Cart");
        emptyCartLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        itemsContainer.getChildren().add(emptyCartLabel);
    } else {
        for (OrderItemResponse item : cartItems) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setPadding(new Insets(5, 10, 5, 10));

            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-font-size: 14px;");
            Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
            priceLabel.setStyle("-fx-font-size: 14px;");
            Label quantityLabel = new Label(String.format("Qty: %d", item.getQuantity()));
            quantityLabel.setStyle("-fx-font-size: 14px;");

            itemBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel);
            itemsContainer.getChildren().add(itemBox);

            total += item.getPrice() * item.getQuantity();
            totalQuantity += item.getQuantity();
        }
    }

    Label totalLabel = new Label(String.format("Total Items: %d, Total Price: $%.2f", totalQuantity, total));
    totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Button placeOrderButton = new Button("Place Order");
    placeOrderButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

    placeOrderButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
    
    placeOrderButton.setOnAction(event -> {
        OrderController.placeOrder(userId);
        
        System.out.println("Order Placed!");
       

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

		delay.setOnFinished(e -> {
            stage.setScene(previousScene);
		});

		delay.play();
    });

    VBox mainContainer = new VBox();
    mainContainer.getChildren().addAll(topBar, itemsContainer);
    if (!cartItems.isEmpty()) {
        mainContainer.getChildren().add(placeOrderButton); // Only show the place order button if there are items in the cart
        mainContainer.getChildren().add(totalLabel); // Only show the place order button if there are items in the cart
    }
    mainContainer.setAlignment(Pos.TOP_CENTER);
    mainContainer.setPadding(new Insets(20));

    Scene scene = new Scene(mainContainer, 400, 600);
    stage.setScene(scene);
    stage.show();
}



}
