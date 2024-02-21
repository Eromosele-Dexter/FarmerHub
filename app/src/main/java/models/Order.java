package models;

public class Order {

    private int customerId;

    private int[] itemIds;

    private double totalPrice;

    public Order(int customerId, int[] itemIds, double totalPrice) {
        this.customerId = customerId;
        this.itemIds = itemIds;
        this.totalPrice = totalPrice;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
