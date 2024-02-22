package views.customerViews;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.OrderItem;
import models.composite_responses.OrderItemResponse;
import utils.DateUtils;

public class OrderHistoryPage {

    public OrderHistoryPage(Stage stage, int userId) {
                stage.setTitle("Order History");

        List<OrderItemResponse> orderItems = getMockOrderItems();

        VBox orderList = new VBox();
        orderList.setAlignment(Pos.TOP_CENTER);
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
                
                new ReviewPage(stage, userId);
            });

            itemBox.getChildren().addAll(dateLabel, itemName, quantity, price, addReviewLink);
            orderList.getChildren().add(itemBox);
        }

        Scene scene = new Scene(orderList, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
        private List<OrderItemResponse> getMockOrderItems() {
            List<OrderItemResponse> items = new ArrayList<>();
            items.add(new OrderItemResponse(6, 0, 5, "Something", "descriptions tuddf", 40, 2, 10, 
                DateUtils.convertEpochToString(new Date().getTime()+"")
            ));
            items.add(new OrderItemResponse(7, 0, 5, "Something 2", "descriptions tuddf", 40, 2, 10, 
                DateUtils.convertEpochToString(new Date().getTime()+"")
            ));
    
        return items;
    }
}
