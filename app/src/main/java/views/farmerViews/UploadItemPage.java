package views.farmerViews;



import controllers.ItemController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Item;

public class UploadItemPage {

    public UploadItemPage(Stage stage) {
        this(stage, null);
    }

    public UploadItemPage(Stage stage, Item item) {
        stage.setTitle(item == null ? "Farmers Hub - Upload Item" : "Farmers Hub - Edit Item");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        
        // Item Name
        Label itemNameLabel = new Label("Item Name:");
        itemNameLabel.setFont(new Font("Arial", 14));
        TextField itemNameField = new TextField();
        grid.add(itemNameLabel, 0, 0);
        grid.add(itemNameField, 0, 1);
        GridPane.setMargin(itemNameField, new Insets(0, 0, 20, 0));
        
        // Description
        Label itemDescriptionLabel = new Label("Description:");
        itemDescriptionLabel.setFont(new Font("Arial", 14));
        TextArea itemDescriptionArea = new TextArea();
        grid.add(itemDescriptionLabel, 0, 2);
        grid.add(itemDescriptionArea, 0, 3);
        GridPane.setMargin(itemDescriptionArea, new Insets(0, 0, 20, 0));
        
        // Price
        Label itemPriceLabel = new Label("Price:");
        itemPriceLabel.setFont(new Font("Arial", 14));
        TextField itemPriceField = new TextField();
        grid.add(itemPriceLabel, 0, 4);
        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER_LEFT);
        Label dollarSign = new Label("$");
        dollarSign.setFont(new Font("Arial", 14));
        
        
        itemPriceField.setPrefWidth(100); 
        itemPriceField.setMaxWidth(200); 

        priceBox.getChildren().addAll(dollarSign, itemPriceField);
        grid.add(priceBox, 0, 5);
        GridPane.setMargin(priceBox, new Insets(0, 0, 20, 0));
        
        // Item Type Dropdown
        Label itemTypeLabel = new Label("Item Type:");
        itemTypeLabel.setFont(new Font("Arial", 14));
        ChoiceBox<String> itemTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Produce", "Machine"));
        grid.add(itemTypeLabel, 0, 6);
        grid.add(itemTypeChoiceBox, 0, 7);
        GridPane.setMargin(itemTypeChoiceBox, new Insets(0, 0, 20, 0));
        
        // Quantity Field
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setFont(new Font("Arial", 14));
        TextField quantityField = new TextField();
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            else if (!newValue.isEmpty() && Integer.parseInt(newValue) > 10000) {
                quantityField.setText("10000");
            }
        });
        quantityField.setPrefWidth(100); 
        quantityField.setMaxWidth(200); 
        grid.add(quantityLabel, 0, 8);
        grid.add(quantityField, 0, 9);
        GridPane.setMargin(quantityField, new Insets(0, 0, 20, 0));
        
        // Condition Dropdown
        Label conditionLabel = new Label("Condition:");
        conditionLabel.setFont(new Font("Arial", 14));
        ChoiceBox<String> conditionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Used", "Fairly Used", "New"));
        grid.add(conditionLabel, 0, 10);
        grid.add(conditionChoiceBox, 0, 11);
        conditionLabel.setVisible(false);
        conditionChoiceBox.setVisible(false);
        
        // Show Condition Dropdown based on Item Type Selection
        itemTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            boolean isMachine = "Machine".equals(newValue);
            conditionLabel.setVisible(isMachine);
            conditionChoiceBox.setVisible(isMachine);
        });
        
        // Upload Button
        String buttonText = item == null ? "Upload" : "Update";
        Button uploadButton = new Button(buttonText);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(uploadButton);
        grid.add(hbBtn, 0,13); // Adjust grid placement for button
        
        // ADD EVENT HANDLER FOR UPLOAD BUTTON
        // uploadButton.setOnAction(ItemController.onUploadButtonClick(itemNameField, itemDescriptionArea, itemPriceField, itemTypeChoiceBox, quantityField, conditionChoiceBox, stage));
        
        
        if (item != null) {
            ItemController.handleSetItemProperties(item.getId(), itemNameField, itemDescriptionArea, itemPriceField, itemTypeChoiceBox, quantityField, conditionChoiceBox);
        }

        Scene scene = new Scene(grid, 450, 700); // Adjusted scene size for layout
        stage.setScene(scene);
        stage.show();
    }

}
