package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import statics.DbConfig;

public class DatabaseSetupUtil {

    public static void createTables() {
        createUsersTable();
    }


    public static void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "username VARCHAR(255) UNIQUE NOT NULL,"
                + "password VARCHAR(255),"
                + "role VARCHAR(50)"
                + ");";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();

            System.out.println("Table 'users' created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating table 'users': " + e.getMessage());
        }
    }
    

}
