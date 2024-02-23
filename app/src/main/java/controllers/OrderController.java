package controllers;

import java.util.List;

import models.Item;
import models.composite_responses.OrderItemResponse;
import repositories.itemRepository.ItemRepository;
import repositories.orderItemRepository.OrderItemRepository;
import services.ItemService;
import services.OrderService;

public class OrderController {

    public static void addToCart(Item item, int quantity, int userId) {
        ItemService itemService = new ItemService(new ItemRepository ());

        OrderService orderService = new OrderService(new OrderItemRepository(), itemService);

        orderService.createOrderItem(item.getId(), userId, quantity, item.getPrice());
    }

    public static List<OrderItemResponse> viewCart(int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        return orderService.getCustomerCart(userId);
    }

    public static void removeFromCart(int itemId, int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        orderService.deleteOrderItem(itemId, userId);
    }

    public static void placeOrder(int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        orderService.placeOrder(userId);
    }
    
    public static List<OrderItemResponse> viewOrderHistory(int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        List<OrderItemResponse> orderItems =  orderService.getOrderHistory(userId);

        return orderItems;
    }

    public static List<OrderItemResponse> viewSalesHistory(int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        List<OrderItemResponse> orderItems =  orderService.getSalesHistory(userId);

        return orderItems;
    }

    public static void reduceQuantityBy1(int orderItemId, int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        orderService.reduceQuantity(orderItemId, userId);
    }

    public static void increaseQuantityBy1(int orderItemId, int userId) {
        OrderService orderService = new OrderService(new OrderItemRepository(), new ItemService(new ItemRepository()));

        orderService.increaseQuantity(orderItemId, userId);
    }
    
    public static int getQuantityAvailable(int itemId) {
        ItemService itemService = new ItemService(new ItemRepository());

        return itemService.handleGetItemById(itemId).getQuantityAvailable();
    }

    public static int getTotalCartQuantity(int userId) {
        List<OrderItemResponse> items  = viewCart(userId);

        int total = 0;

        for (OrderItemResponse item : items) {
            total += item.getQuantity();
        }

        return total;
    }
}
