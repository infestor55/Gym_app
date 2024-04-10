package Controllers;

import Models.Users;
import Services.ServiceUsers;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private final ServiceUsers serviceUsers;

    public UserController() {
        this.serviceUsers = new ServiceUsers();
    }

    public void addUser(Users user) {
        try {
            serviceUsers.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Users user) {
        try {
            serviceUsers.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Users user) {
        try {
            serviceUsers.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Users> getAllUsers() {
        try {
            return serviceUsers.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
