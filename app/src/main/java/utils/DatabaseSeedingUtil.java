package utils;

import models.Customer;
import models.Farmer;
import models.User;
import repositories.userRepository.IUserRepository;
import repositories.userRepository.UserRepository;

public class DatabaseSeedingUtil {

    private IUserRepository userRepository;

    public DatabaseSeedingUtil() {
        userRepository = new UserRepository();
    }

    public void seedDatabase() {
        DatabaseSetupUtil.createTables();
        seedUsersTable();
    }

    public  void seedUsersTable() {
        User[] users = {
            new Farmer("John", "Doe", "johndoe", "password123"),
            new Customer("Jane", "Doe", "janedoe", "password456"),
            new Farmer("Jim", "Beam", "jimbeam", "password789")
        };

        for (User user : users) {
            userRepository.createUser(user);
        }

        System.out.println("Users table seeded successfully.");
    }
    
}
