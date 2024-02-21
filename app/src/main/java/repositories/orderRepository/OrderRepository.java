package repositories.orderRepository;

import models.Order;
import utils.DatabaseUtil;
import statics.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {

    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, item_ids, total_price) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Integer[] itemIdsArray = Arrays.stream(order.getItemIds())
                    .boxed()
                    .toArray(Integer[]::new);

            pstmt.setInt(1, order.getCustomerId());

            pstmt.setArray(2, conn.createArrayOf("INTEGER", itemIdsArray));

            pstmt.setDouble(3, order.getTotalPrice());

            pstmt.executeUpdate();

            System.out.println("Order created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("No order found with ID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    public void updateOrder(Order order, int orderId) {
        String sql = "UPDATE orders SET customer_id = ?, item_ids = ?, total_price = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

             Integer[] itemIdsArray = Arrays.stream(order.getItemIds())
                    .boxed()
                    .toArray(Integer[]::new);

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setArray(2, conn.createArrayOf("INTEGER", itemIdsArray));
            pstmt.setDouble(3, order.getTotalPrice());
            pstmt.setInt(4, orderId); // Assuming Order class has this method

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("No order found with ID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating order: " + e.getMessage());
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Array itemIds = rs.getArray("item_ids");
                Integer[] itemIdsArray = (Integer[]) itemIds.getArray();

                order = new Order(rs.getInt("customer_id"), 
                convertIntegerToInt(itemIdsArray), 
                                  rs.getDouble("total_price"));
                
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order: " + e.getMessage());
        }
        return order;
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Array itemIds = rs.getArray("item_ids");
                Integer[] itemIdsArray = (Integer[]) itemIds.getArray();
                Order order = new Order(rs.getInt("customer_id"),
                convertIntegerToInt(itemIdsArray), 
                                        rs.getDouble("total_price"));

                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving orders for customer ID: " + customerId + " - " + e.getMessage());
        }
        return orders;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Array itemIds = rs.getArray("item_ids");
                Integer[] itemIdsArray = (Integer[]) itemIds.getArray();
                Order order = new Order(rs.getInt("customer_id"),
                                        convertIntegerToInt(itemIdsArray), // Convert to primitive int array if necessary
                                        rs.getDouble("totalPrice"));
    
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all orders: " + e.getMessage());
        }
        return orders;
    }

    public static int[] convertIntegerToInt(Integer[] integerArray) {

        int[] intArray = new int[integerArray.length];
        

        for (int i = 0; i < integerArray.length; i++) {
            intArray[i] = integerArray[i];
        }
        
        return intArray;
    }
    
}

