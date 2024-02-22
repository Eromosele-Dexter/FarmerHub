package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import models.Item;
import models.OrderItem;
import models.composite_responses.OrderItemResponse;
import repositories.orderItemRepository.IOrderItemRepository;

public class OrderService {
    private IOrderItemRepository orderItemRepository;
    private ItemService itemService;

    public OrderService(IOrderItemRepository orderItemRepository, ItemService itemService) {
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
    }

    public void createOrderItem(int itemId, int customerId, int quantity, double price) {

        OrderItem orderItem = orderItemRepository.getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(itemId, customerId);

        if(orderItem != null){
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItem.setDateOrdered(new Date().getTime()+"");
            orderItemRepository.updateOrderItem(orderItem);

        } else {
            orderItem = new OrderItem(itemId, customerId, quantity, price, new Date().getTime()+"");
            orderItemRepository.createOrderItem(orderItem);
        }
    }

    public List<OrderItemResponse> getCustomerCart(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        HashMap<Integer, OrderItemResponse> orderItemMap = new HashMap<Integer, OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {

            if(!orderItem.hasBeenPurchased()){

                if(orderItemMap.containsKey(orderItem.getItemId())){

                    OrderItemResponse orderItemResponse = orderItemMap.get(orderItem.getItemId());
                    orderItemResponse.setQuantity(orderItemResponse.getQuantity() + orderItem.getQuantity());

                } else {
                    orderItemMap.put(orderItem.getItemId(), getOrderItemResponse(orderItem));
                }
            }
        }

        for (OrderItemResponse orderItemResponse : orderItemMap.values()) {
            orderItemResponses.add(orderItemResponse);
        }

        return orderItemResponses;
    }

    public List<OrderItemResponse> getOrdersByCustomerIdIncludingPurchased(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }

    // public OrderItemResponse getOrderItemByItemIdAndCustomerId(int itemId, int customerId) {
    //     OrderItem orderItem = orderItemRepository.getOrderItemByItemIdAndCustomerId(itemId, customerId);

    //     return getOrderItemResponse(orderItem);     
    // }

    public void updateOrder(List<OrderItem> orderItems, String dateTime) {

        String date = new Date().getTime()+"";

        for (OrderItem orderItem : orderItems) {
            orderItem.setDateOrdered(date);
            orderItemRepository.updateOrderItem(orderItem);
        }
    }

    public void placeOrder(int customerId) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        List<OrderItem> customerCart = orderItemRepository.getOrderItemsByCustomerId(customerId);

        System.out.println("Customer Cart: " + customerCart.size());

        HashMap<Integer, OrderItemResponse> orderItemMap = new HashMap<Integer, OrderItemResponse>();

        for (OrderItem orderItem : customerCart) {
            if(!orderItem.hasBeenPurchased()){

                if(orderItemMap.containsKey(orderItem.getItemId())){
                    OrderItemResponse orderItemResponse = orderItemMap.get(orderItem.getItemId());
                    orderItemResponse.setQuantity(orderItemResponse.getQuantity() + orderItem.getQuantity());

                } else {
                    orderItemMap.put(orderItem.getItemId(), getOrderItemResponse(orderItem));
                }
            }
        }

        System.out.println("Order Item Map: " + orderItemMap.size());

        for (OrderItemResponse orderItemResponse : orderItemMap.values()) {
            OrderItem orderItem = new OrderItem(orderItemResponse.getItemId(), orderItemResponse.getCustomerId(), orderItemResponse.getQuantity(), orderItemResponse.getPrice(), new Date().getTime()+"");

            orderItem.setId(orderItemResponse.getOrderItemId());

            orderItem.setHasBeenPurchased(true);

            orderItems.add(orderItem);

            System.out.println("Order Item: " + orderItemResponse.getItemId() + " " + orderItemResponse.getCustomerId() + " " + orderItemResponse.getQuantity() + " " + orderItemResponse.getPrice());
        }

        updateOrder(orderItems, new Date().getTime()+"");
    }

    public List<OrderItemResponse> getItemSoldHistory(int farmerId) {
        List<Item> farmersItems = itemService.handleGetItemsByFarmerId(farmerId);
        
        List<Integer> itemIds = new ArrayList<Integer>();

        for (Item item : farmersItems) {
            itemIds.add(item.getId());
        }

        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByItemIds(itemIds);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            if(orderItem.hasBeenPurchased())
                orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }

    public List<OrderItemResponse> getOrderHistory(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            if(orderItem.hasBeenPurchased())
                orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }

    public void deleteOrderItem(int itemId, int customerId) {
        orderItemRepository.deleteOrderItem(itemId, customerId);
    }

    private OrderItemResponse getOrderItemResponse(OrderItem orderItem) {
        Item item = itemService.handleGetItemById(orderItem.getItemId());

       return new OrderItemResponse(orderItem.getItemId(), orderItem.getId(), item.getFarmerId(), item.getName(),
        item.getDescription(), item.getPrice(), orderItem.getCustomerId(),
         orderItem.getQuantity(), orderItem.getDateOrdered());
    }
    
}
