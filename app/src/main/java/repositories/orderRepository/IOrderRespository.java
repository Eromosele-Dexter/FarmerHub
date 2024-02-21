package repositories.orderRepository;

import java.util.List;

import models.Order;

public interface IOrderRespository {
    public void createOrder(int customerId, int[] itemIds, double totalPrice);
    public void deleteOrder(int orderId);
    public void updateOrder(int orderId, int customerId, int[] itemIds, double totalPrice);
    public Order  getOrderById(int orderId);
    public List<Order> getOrdersByCustomerId(int customerId);
    public List<Order>  getOrders();
}
