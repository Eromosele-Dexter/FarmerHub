package controllers;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;
import models.Machine;
import models.Produce;
import models.User;
import repositories.itemRepository.ItemRespository;
import repositories.userRepository.UserRepository;
import services.ItemService;
import services.UserService;
import views.farmerViews.FarmerLandingPage;

// TODO: try to make class non-static so Item controller doesnt depend on implementation of ItemService and ItemRepository
public class ItemController {


    public static List<Item> handleGetItemsByFarmerId(int farmerId) {

        ItemService itemService = new ItemService(new ItemRespository()); 

        List<Item> items = itemService.handleGetItemsByFarmerId(farmerId);

        return items.isEmpty() ? null :  items;
    }

    public static void handleSetItemProperties(int itemId, TextField itemNameField, TextArea itemDescriptionArea, TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox) {
        ItemService itemService = new ItemService(new ItemRespository());

        Item item = itemService.handleGetItemById(itemId);


        itemNameField.setText(item.getName());
        itemDescriptionArea.setText(item.getDescription());
        itemPriceField.setText(String.valueOf(item.getPrice()));
        quantityField.setText(String.valueOf(item.getQuantityAvailable()));

        if (item instanceof Produce) {

            itemTypeBox.setValue("Produce");
        }
        else{
           
            Machine tempItem = (Machine) item;

            conditionBox.setValue(tempItem.getCondition());

            itemTypeBox.setValue("Machine");
        }
    }



    public static EventHandler<ActionEvent> createItemOnButtonClick(TextField itemNameField, TextArea itemDescriptionArea,
            TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox, int farmerId, Stage stage, Text actionTarget) {
                ItemService itemService = new ItemService(new ItemRespository());

                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String name = itemNameField.getText();
                        String description = itemDescriptionArea.getText();
                        double price = itemPriceField.getText().isEmpty()? 0.0 : Double.parseDouble(itemPriceField.getText());
                        int quantity = quantityField.getText().isEmpty()? 0 : Integer.parseInt(quantityField.getText());
                        String type = itemTypeBox.getValue();
                        String condition = conditionBox.getValue();

                        boolean isValid = itemService.handleCreateItem(name, description, price, quantity, type, farmerId, actionTarget, condition);

                        if(!isValid) {
                            return;
                        }

                        UserService userService = new UserService(new UserRepository());

		                User farmer = userService.handleGetUserById(farmerId);

                        new FarmerLandingPage(stage, farmer);
                    }
                };
    }

    public static EventHandler<ActionEvent> updateItemOnButtonClick(TextField itemNameField, TextArea itemDescriptionArea,
        TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox, int farmerId, Stage stage, Text actionTarget) {
        ItemService itemService = new ItemService(new ItemRespository());

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = itemNameField.getText();
                String description = itemDescriptionArea.getText();
                double price = Double.parseDouble(itemPriceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String type = itemTypeBox.getValue();
                String condition = conditionBox.getValue();

                boolean isValid = itemService.handleUpdateItem(name, description, price, quantity, type, farmerId, actionTarget, condition.toUpperCase());

                if(!isValid) {
                    return;
                }

                UserService userService = new UserService(new UserRepository());

                User farmer = userService.handleGetUserById(farmerId);

                new FarmerLandingPage(stage, farmer);
            }
        };
    }
    
    public static void deleteItem(Item item) {
        ItemService itemService = new ItemService(new ItemRespository());

        itemService.handleDeleteItem(item);
    }
    
}
