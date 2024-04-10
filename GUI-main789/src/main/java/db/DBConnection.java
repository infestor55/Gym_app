package db;

import java.sql.* ;
import Models.Users;
import Services.ServiceUsers;

public class DBConnection {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    static Connection con;
    static Statement ste;

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection established");

            // Test adding a user
            addUser("John Doe", "john@example.com");

            // Test reading users
            readUsers();

            // Test updating a user's email
            updateUserEmail("John Doe", "john.doe@example.com");

            // Test deleting a user
            deleteUser("John Doe");

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (ste != null) ste.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void addUser(String userName, String userEmail) {
        Users user = new Users(userName, userEmail);
        ServiceUsers serviceUsers = new ServiceUsers();
        try {
            serviceUsers.add(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void readUsers() {
        ServiceUsers serviceUsers = new ServiceUsers();
        try {
            serviceUsers.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void updateUserEmail(String userName, String newEmail) {
        Users user = new Users(userName, newEmail);
        ServiceUsers serviceUsers = new ServiceUsers();
        try {
            serviceUsers.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void deleteUser(String userName) {
        Users user = new Users(userName, "");
        ServiceUsers serviceUsers = new ServiceUsers();
        try {
            serviceUsers.delete(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}