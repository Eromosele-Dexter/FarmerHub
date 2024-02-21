package repositories.orderItemRepository;


import models.OrderItem;
import java.util.List;

public interface IOrderItemRepository {
    void createOrderItem(OrderItem orderItem);
    OrderItem getOrderItemByItemIdAndCustomerId(int itemId, int customerId);
    List<OrderItem> getOrderItemsByCustomerId(int customerId);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(int itemId, int customerId);
}
