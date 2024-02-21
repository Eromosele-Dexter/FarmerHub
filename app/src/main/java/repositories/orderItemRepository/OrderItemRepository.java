package repositories.orderItemRepository;


import models.OrderItem;
import utils.DatabaseUtil;
import statics.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository implements IOrderItemRepository {


    public void createOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (item_id, customer_id, quantity, price, date_ordered) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItem.getItemId());
            pstmt.setInt(2, orderItem.getCustomerId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getPrice());
            pstmt.setString(5, orderItem.getDateOrdered());

            pstmt.executeUpdate();
            System.out.println("Order item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating order item: " + e.getMessage());
        }
    }

    public OrderItem getOrderItemByItemIdAndCustomerId(int itemId, int customerId) {
        String sql = "SELECT * FROM order_items WHERE item_id = ? AND customer_id = ?";
        OrderItem orderItem = null;

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.setInt(2, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order item: " + e.getMessage());
        }
        return orderItem;
    }

    public List<OrderItem> getOrderItemsByCustomerId(int customerId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order items for customer ID: " + customerId + " - " + e.getMessage());
        }
        return orderItems;
    }

    public void updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET quantity = ?, price = ?, date_ordered = ? WHERE item_id = ? AND customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItem.getQuantity());
            pstmt.setDouble(2, orderItem.getPrice());
            pstmt.setString(3, orderItem.getDateOrdered());
            pstmt.setInt(4, orderItem.getItemId());
            pstmt.setInt(5, orderItem.getCustomerId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Order item updated successfully.");
            } else {
                System.out.println("No order item found with specified item ID and customer ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating order item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(int itemId, int customerId) {
        String sql = "DELETE FROM order_items WHERE item_id = ? AND customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.setInt(2, customerId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order item deleted successfully.");
            } else {
                System.out.println("No order item found with provided item ID and customer ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order item: " + e.getMessage());
        }
    }
}

