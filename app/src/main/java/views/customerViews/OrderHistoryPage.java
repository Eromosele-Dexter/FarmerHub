package views.customerViews;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.OrderItem;
import models.composite_responses.OrderItemResponse;
import utils.DateUtils;

public class OrderHistoryPage {

   public OrderHistoryPage(Stage stage, int userId, Scene previousScene) {
    stage.setTitle("Order History");

    // Top bar for back button and page title
    HBox topBar = new HBox();
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.setPadding(new Insets(10));
    topBar.setSpacing(10);

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> goBack(stage, previousScene));

    Label pageTitle = new Label("Your Order History");
    pageTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

    // Center pageTitle in the remaining space
    HBox.setHgrow(pageTitle, javafx.scene.layout.Priority.ALWAYS);
    pageTitle.setMaxWidth(Double.MAX_VALUE);
    pageTitle.setAlignment(Pos.CENTER);

    topBar.getChildren().addAll(backButton, pageTitle);

    VBox mainLayout = new VBox(5);
    mainLayout.setAlignment(Pos.TOP_CENTER);
    mainLayout.setPadding(new Insets(10));
    mainLayout.getChildren().add(topBar); // Add the topBar at the top of the main layout

    List<OrderItemResponse> orderItems = getMockOrderItems();

    VBox orderList = new VBox();
    orderList.setPadding(new Insets(10));
    orderList.setSpacing(10);

    for (OrderItemResponse item : orderItems) {
        VBox itemBox = new VBox(5);
        itemBox.setPadding(new Insets(10));
        itemBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        Label dateLabel = new Label("Date Ordered: " + item.getDateOrdered().toString());
        Text itemName = new Text("Item: " + item.getName());
        Text quantity = new Text("Quantity: " + item.getQuantity());
        Text price = new Text("Price: $" + item.getPrice());

        Hyperlink addReviewLink = new Hyperlink("Add Review");
        addReviewLink.setTextFill(Color.BLUE);
        addReviewLink.setOnAction(e -> {
            System.out.println("Navigating to Add Review Page for " + item.getName());
            new ReviewPage(stage, item.getItemId(), stage.getScene());
        });

        itemBox.getChildren().addAll(dateLabel, itemName, quantity, price, addReviewLink);
        orderList.getChildren().add(itemBox);
    }

    mainLayout.getChildren().add(orderList); // Add orderList to the main layout

    Scene scene = new Scene(mainLayout, 400, 600);
    stage.setScene(scene);
    stage.show();
}

    
    
    private List<OrderItemResponse> getMockOrderItems() {
            List<OrderItemResponse> items = new ArrayList<>();
            // items.add(new OrderItemResponse(6, 0, 5, "Something", "descriptions tuddf", 40, 2, 10, 
            //     DateUtils.convertEpochToString(new Date().getTime()+"")
            // ));
            // items.add(new OrderItemResponse(7, 0, 5, "Something 2", "descriptions tuddf", 40, 2, 10, 
            //     DateUtils.convertEpochToString(new Date().getTime()+"")
            // ));
    
        return items;
    }

    private void goBack(Stage stage, Scene previousScene) {
        stage.setScene(previousScene);
    }
}
