package repositories.userRepository;

import models.User;

public interface IUserRepository {
    public void createUser(User user);
    public void deleteUser(String userName);
    public void updateUser(User user, String role);
    public User getUserByUsername(String userName);
}
