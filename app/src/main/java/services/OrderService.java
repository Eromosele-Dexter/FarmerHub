package services;

import java.util.List;
import models.Order;
import repositories.orderRepository.IOrderRespository;


public class OrderService {
    private IOrderRespository orderRepository;
    private ItemService itemService;

    public OrderService(IOrderRespository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public void createOrder(int customerId, int[] itemIds, double totalPrice) {

        orderRepository.createOrder(customerId, itemIds, totalPrice);
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    public List<Order> getOrders() {
        itemService.handleGetItemsByIds(null); //TODO
        return orderRepository.getOrders();
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteOrder(orderId);
    }

    public void updateOrder(int orderId, int customerId, int[] itemIds, double totalPrice) {
        orderRepository.updateOrder(orderId, customerId, itemIds, totalPrice);
    }

    

}
