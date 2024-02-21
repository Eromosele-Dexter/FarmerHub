package controllers;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Item;
import models.Machine;
import models.Produce;
import repositories.itemRepository.ItemRespository;
import services.ItemService;

// TODO: try to make class non-static so Item controller doesnt depend on implementation of ItemService and ItemRepository
public class ItemController {


    public static List<Item> handleGetItemsByFarmerId(int farmerId) {
        System.out.println("hi2: " + farmerId);

        ItemService itemService = new ItemService(new ItemRespository()); 

        List<Item> items = itemService.handleGetItemsByFarmerId(farmerId);

        System.out.println("Items: " + items.isEmpty());

        // System.out.println("Items: " + items.get(0).getName());

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
            TextField itemPriceField, Stage stage) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'onUploadButtonClick'");
        return null;
    }

    public static EventHandler<ActionEvent> updateItemOnButtonClick(TextField itemNameField, TextArea itemDescriptionArea,
        TextField itemPriceField, Stage stage) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'onUploadButtonClick'");
    return null;
    }
    
    public static void deleteItem(Item item) {
        // TODO Auto-generated method stub
        return;
    }
    
}
