package services;

import java.util.List;

import javafx.scene.text.Text;
import models.Item;
import models.Machine;
import models.Produce;
import repositories.itemRepository.IItemRepository;
import statics.ItemStatics;

public class ItemService {
    
    private IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void handleCreateItem(String name, String description, double price, int quantity, String type, int farmerId, Text actionTarget, String condition) {
        String validationMessage = validateCreateItem(name, description, price, quantity, type);

        if (!validationMessage.isEmpty()) {

            actionTarget.setText(validationMessage);

            return;
        }

        Item newItem = null;

        if(type.toUpperCase().equals(ItemStatics.MACHINE)){
            newItem = new Machine(farmerId, name, description, price, quantity, condition);
        }
        else {
            newItem = new Produce(farmerId, name, description, price, quantity);
        }

        itemRepository.createItem(newItem);

        actionTarget.setText("Item Created Successfully");
    }

    public void handleUpdateItem(String name, String description, double price, int quantity, String type, int farmerId, Text actionTarget, String condition) {
        String validationMessage = validateCreateItem(name, description, price, quantity, type);

        if (!validationMessage.isEmpty()) {

            actionTarget.setText(validationMessage);

            return;
        }

        Item newItem = null;

        if(type.toUpperCase().equals(ItemStatics.MACHINE)){
            newItem = new Machine( farmerId, name, description, price, quantity, condition);
        }
        else {
            newItem = new Produce(farmerId, name, description, price, quantity);
        }

        itemRepository.updateItem(newItem);

        actionTarget.setText("Item Updated Successfully");
    }


    public Item handleGetItemById(int itemId) {
        return itemRepository.getItemById(itemId);
    }

    public List<Item> handleGetItemsByFarmerId(int farmerId) {
        return itemRepository.getItemsByFarmerId(farmerId);
    }

    public List<Item> handleGetAllItems() {
        return itemRepository.getAllItems();
    }

    public void handleDeleteItem(Item item, Text actionTarget) {
        itemRepository.deleteItem(item);
        actionTarget.setText("Item Deleted Successfully");
    }

    private String validateCreateItem(String name, String description, double price, int quantity, String type) {
        StringBuilder missingFields = new StringBuilder("Missing fields: ");

        if (name == null || name.trim().isEmpty()) missingFields.append("\nName, ");

        if (description == null || description.trim().isEmpty()) missingFields.append("\nDescription, ");

        if (price <= 0) missingFields.append("\nPrice, ");

        if (quantity <= 0) missingFields.append("\nQuantity, ");

        if (type == null || type.trim().isEmpty()) missingFields.append("\nType, ");

               if (missingFields.length() > "Missing fields: ".length()) {

            missingFields.setLength(missingFields.length() - 2);

            return missingFields.toString();

        } else {
            // Return empty string if no fields are missing
            return "";
        }
    }

    
}
