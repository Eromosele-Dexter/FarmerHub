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
    
}
