package views.customerViews;

import java.util.List;

import controllers.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.composite_responses.OrderItemResponse;


public class CartPage {
     public CartPage(Stage stage, int userId) {
                stage.setTitle("Cart Overview");

        List<OrderItemResponse> cartItems = OrderController.viewCart(userId);

        VBox itemsContainer = new VBox();
        itemsContainer.setAlignment(Pos.TOP_CENTER);
        itemsContainer.setSpacing(5);
        itemsContainer.setPadding(new Insets(10));

        double total = 0;
        for (OrderItemResponse item : cartItems) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(item.getName());
            Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
            Label quantityLabel = new Label(String.format("Qty: %d", item.getQuantity()));

            itemBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel);
            itemsContainer.getChildren().add(itemBox);

            total += item.getPrice() * item.getQuantity();
        }

        Label totalLabel = new Label(String.format("Total Price: $%.2f", total));
        totalLabel.setPadding(new Insets(10, 0, 10, 0));

        Button placeOrderButton = new Button("Place Order");
        
        placeOrderButton.setOnAction(event -> {
            // Place order logic here
            System.out.println("Order Placed!");
        });

        VBox mainContainer = new VBox(10, itemsContainer, totalLabel, placeOrderButton);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(20));

        Scene scene = new Scene(mainContainer, 400, 600);
        stage.setScene(scene);
        stage.show();
     }
    
}
