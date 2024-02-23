package views.farmerViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ItemController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Item;
import models.Machine;
import models.User;
import utils.StringUtils;

// public class FarmerLandingPage {


// 	public FarmerLandingPage(Stage stage, User user) {
// 		int userId = user.getId();

// 	    // Top bar for greeting and add item link
//         HBox topBar = new HBox();
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setPadding(new Insets(15));
//         topBar.setSpacing(10);
        
//         Label greeting = new Label("Hi " + user.getFirstName()+"! 👋");
//         greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

//         // Links container to group Order History and Cart, aligned at the top right
// 		HBox linksContainer = new HBox(5); // Spacing of 5px between links
// 		linksContainer.setAlignment(Pos.CENTER_RIGHT);
// 		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS); // This will push the linksContainer to the right

// 		Hyperlink salesHistoryLink = new Hyperlink("Sales History 📜");

// 		salesHistoryLink.setStyle("-fx-font-size: 16px;");

// 		Hyperlink uploadItemLink = new Hyperlink("Upload Item 💭");

// 		uploadItemLink.setStyle("-fx-font-size: 16px;");
        
//   		// Add both links to the links container
//           linksContainer.getChildren().addAll(salesHistoryLink, uploadItemLink);

//           // Add greeting and links container to the top bar
//           topBar.getChildren().addAll(greeting, linksContainer);
        
//         // Main container VBox inside the ScrollPane
//         VBox vbox = new VBox();
//         vbox.setPadding(new Insets(10));
//         vbox.setSpacing(8);

//         List<Item> fetchedItems = ItemController.handleGetItemsByFarmerId(userId);
//         List<Item> items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems) : FXCollections.observableArrayList();

//         for (Item item : items) {
//             VBox card = new VBox(10);
//             card.setPadding(new Insets(15));
//             card.setStyle("-fx-border-color: lightgrey; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f9f9f9;");

//             Label itemName = new Label("Name: " + item.getName());
//             itemName.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");
//             Label itemDescription = new Label("Description: " + item.getDescription());
//             Label itemPrice = new Label(String.format("Price: $%s", StringUtils.formatNumberPrice(item.getPrice())));
//             Label itemQuantity = new Label("Quantity Available: " + item.getQuantityAvailable());

//             Label itemCondition = null;
//             if (item instanceof Machine) {
//                 Machine machine = (Machine) item;
//                 itemCondition = new Label("Condition: " + StringUtils.capitalize(machine.getCondition()).replace("_", " "));
//             }

//             Button updateButton = new Button("Update");
//             Button deleteButton = new Button("Delete");

//             HBox buttonsBox = new HBox(10);
//             buttonsBox.setAlignment(Pos.CENTER_RIGHT);
//             buttonsBox.getChildren().addAll(updateButton, deleteButton);

//             updateButton.setOnAction(e -> {
//                 Scene currentScene = stage.getScene();
//                 showUpdateItemPage(stage, item, userId, currentScene);
//             });
//             deleteButton.setOnAction(e -> {
//                 ItemController.deleteItem(item);
//                 vbox.getChildren().remove(card); 
//             });

//             card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);

//             if (itemCondition != null) {
//                 card.getChildren().add(itemCondition);
//             }

//             card.getChildren().add(buttonsBox);
//             vbox.getChildren().add(card);
//         }

//         // ScrollPane to make the VBox scrollable
//         ScrollPane scrollPane = new ScrollPane();
//         scrollPane.setContent(vbox);
//         scrollPane.setFitToWidth(true); 
//         scrollPane.setPadding(new Insets(10));

// 		BorderPane root = new BorderPane();
//         root.setTop(topBar);
//         root.setCenter(scrollPane);

//         // Setting the scrollPane as the scene root
//         Scene scene = new Scene(root, 400, 600);
//         salesHistoryLink.setOnAction(e -> showSalesHistoryPage(stage, userId,scene));
//         uploadItemLink.setOnAction(e -> showUploadItemPage(stage, userId,scene));
//         stage.setScene(scene);
//         stage.show();
//     }

//     private void showSalesHistoryPage(Stage stage, int userId, Scene scene) {
//         new SalesHistoryPage(stage, userId, scene);
//     }

//     private void showUpdateItemPage(Stage stage, Item item, int userId, Scene scene) {

//         new UploadItemPage(stage, item, userId, scene);
//     }

// 	private void showUploadItemPage(Stage stage, int userId, Scene scene) {
   
//         new UploadItemPage(stage, null, userId, scene);
//     }
// }

public class FarmerLandingPage {
    private VBox vbox;
    private Stage stage;
    private int userId;
    private User user;
    private Hyperlink goToCartLink;
    private Hyperlink orderHistoryLink;
    private Map<Integer, Integer> itemQuantities = new HashMap<>(); // Item ID to Quantity

    public FarmerLandingPage(Stage stage, User user) {
        this.stage = stage;
        this.userId = user.getId();
        this.user = user;

        initializeUI();
        startItemFetchLoop();
    }

    private void initializeUI() {
        vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

		HBox topBar = new HBox();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(15));
		topBar.setSpacing(10);

        Label greeting = new Label("Hi " + user.getFirstName()+"! 👋");
        greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

        
		HBox linksContainer = new HBox(5); 
		linksContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS);

		Hyperlink salesHistoryLink = new Hyperlink("Sales History 📜");

		salesHistoryLink.setStyle("-fx-font-size: 16px;");

		Hyperlink uploadItemLink = new Hyperlink("Upload Item 💭");

		uploadItemLink.setStyle("-fx-font-size: 16px;");
        
        linksContainer.getChildren().addAll(salesHistoryLink, uploadItemLink);

        topBar.getChildren().addAll(greeting, linksContainer);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true); 
        scrollPane.setPadding(new Insets(10));

		BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane); // Wrap vbox in a ScrollPane for scrolling support

        Scene scene = new Scene(mainLayout, 400, 600);
        salesHistoryLink.setOnAction(e -> showSalesHistoryPage(stage, userId,scene));
        uploadItemLink.setOnAction(e -> showUploadItemPage(stage, userId,scene));
        stage.setScene(scene);
        stage.show();
    }

   private void startItemFetchLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
            fetchAndDisplayItems();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void fetchAndDisplayItems() {
        Platform.runLater(() -> {
            List<Item> fetchedItems = ItemController.handleGetItemsByFarmerId(userId); 
            List<Item> items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems) : FXCollections.observableArrayList();
            vbox.getChildren().clear(); // Clear existing items

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

                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                HBox buttonsBox = new HBox(10);
                buttonsBox.setAlignment(Pos.CENTER_RIGHT);
                buttonsBox.getChildren().addAll(updateButton, deleteButton);

                updateButton.setOnAction(e -> {
                    Scene currentScene = stage.getScene();
                    showUpdateItemPage(stage, item, userId, currentScene);
                });
                deleteButton.setOnAction(e -> {
                    ItemController.deleteItem(item);
                    vbox.getChildren().remove(card); 
                });

                card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);

                if (itemCondition != null) {
                    card.getChildren().add(itemCondition);
                }

                card.getChildren().add(buttonsBox);
                vbox.getChildren().add(card);
            }
        });
    }

    private void showSalesHistoryPage(Stage stage, int userId, Scene scene) {
        new SalesHistoryPage(stage, userId, scene);
    }

    private void showUpdateItemPage(Stage stage, Item item, int userId, Scene scene) {

        new UploadItemPage(stage, item, userId, scene);
    }

	private void showUploadItemPage(Stage stage, int userId, Scene scene) {
   
        new UploadItemPage(stage, null, userId, scene);
    }
}