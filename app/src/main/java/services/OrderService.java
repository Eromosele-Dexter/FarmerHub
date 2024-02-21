package services;

import java.util.Date;
import java.util.List;
import models.OrderItem;
import repositories.orderItemRepository.IOrderItemRepository;

public class OrderService {
    private IOrderItemRepository orderItemRepository;
    private ItemService itemService;

    public OrderService(IOrderItemRepository orderItemRepository, ItemService itemService) {
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
    }

    public void createOrderItem(int orderId, int itemId, int customerId, int quantity, double price) {
        OrderItem orderItem = new OrderItem(
            itemId,
            customerId,
            quantity,
            price,
            new Date().getTime()+""
        );

        orderItemRepository.createOrderItem(orderItem);
    }

    public List<OrderItem> getOrdersByCustomerId(int customerId) {
        return orderItemRepository.getOrderItemsByCustomerId(customerId);
    }

    // public List<OrderItem> getOrders() {

     
    // }

    public void deleteOrder(int orderId) {

    }

    public void updateOrder(List<OrderItem> orderItems, String dateTime) {
 // TODO: update order date time for all orders in order, this is called when order is placed
    }

    public void placeOrder(int customerId, int[] itemIds, int[] quantities) {
 
    }
    

}
