package views.farmerViews;

import java.util.List;

import controllers.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;
import models.User;

public class FarmerLandingPage {
	private ObservableList<Item> items; 

	public FarmerLandingPage(Stage stage, int userId) {
		stage.setTitle("Farmers Hub - View Items");
				VBox vbox = new VBox();
				vbox.setPadding(new Insets(10));
				vbox.setSpacing(8);

				System.out.println("hi1");

				List<Item> fetchedItems = ItemController.handleGetItemsByFarmerId(userId);

				items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems): FXCollections.observableArrayList(); // Assuming you have such a method

				for (Item item : items) {
					HBox itemBox = new HBox(10);
					itemBox.setAlignment(Pos.CENTER_LEFT);

					Label itemName = new Label(item.getName());
					Button updateButton = new Button("Update");
					Button deleteButton = new Button("Delete");

					updateButton.setOnAction(e -> showUpdateItemPage(stage, item));
					deleteButton.setOnAction(e -> {
						ItemController.deleteItem(item);
						vbox.getChildren().remove(itemBox); // Remove the item's HBox from the VBox
					});

					itemBox.getChildren().addAll(itemName, updateButton, deleteButton);
					vbox.getChildren().add(itemBox);
				}

				Scene scene = new Scene(vbox, 400, 600);
				stage.setScene(scene);
				stage.show();
    }

    private void showUpdateItemPage(Stage stage, Item item) {
        // Assuming UploadItemPage has been modified to accept an Item object for editing
        new UploadItemPage(stage, item);
    }

}
