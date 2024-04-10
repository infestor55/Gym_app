package Services;

import Models.Responsible;
import java.sql.*;
import java.util.List;

public class ServiceResponsible implements IService<Responsible> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Responsible responsible) throws SQLException {
        // Add your implementation here
        String username = responsible.getUserNameDB();
        String email = responsible.getUserEmailDB();
        double finance = responsible.getFinance();

        // Check if the UserNameDB exists in the Users table
        boolean userExists = checkUserExists(username);

        if (userExists) {
            createResponsible(username, email, finance);
            /*responsibleList.add(responsible);
            clearFields();*/
        } else {
            //showAlert(Alert.AlertType.ERROR, "Error", "User does not exist", "The provided username does not exist in the Users table.");
        }
    }

    public boolean verify(Responsible user) throws SQLException {
        String userName = user.getUserNameDB();
        boolean verif = checkIfUsernameExists(userName);
        return verif;
    }

    private static boolean checkIfUsernameExists(String userName) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String query = "SELECT * FROM Responsible WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // If rs.next() is true, it means username exists
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
        return false;
    }

    private boolean checkUserExists(String username) {
        boolean exists = false;
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String query = "SELECT UserNameDB FROM Users WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            exists = rs.next(); // If the result set has any rows, the user exists
        } catch (SQLException e) {
            e.printStackTrace();
            //showAlert(Alert.AlertType.ERROR, "Error", "Error checking user existence", e.getMessage());
        }
        return exists;
    }
    @Override
    public void delete(Responsible responsible) throws SQLException {
        String userName = responsible.getUserNameDB();
        deleteResponsible(userName);
    }

    @Override
    public void update(Responsible responsible) throws SQLException {
        String userName = responsible.getUserNameDB();
        double newFinance = responsible.getFinance();
        updateResponsibleFinance(userName, newFinance);
    }

    @Override
    public List<Responsible> readAll() throws SQLException {
        readResponsibles();
        return null;
    }

    @Override
    public Responsible findbyId(int id) throws SQLException {
        return null;
    }

    private static void createResponsible(String userName, String userEmail, double finance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Responsible (UserNameDB, UserEmailDB, Finance) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setString(2, userEmail);
            pst.setDouble(3, finance);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Responsible added successfully");
            } else {
                System.out.println("Failed to add responsible");
            }
        } catch (SQLException e) {
            System.err.println("Error adding responsible: " + e.getMessage());
        }
    }

    private static void readResponsibles() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Responsible";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Responsible Name: " + rs.getString("UserNameDB") + ", Email: " + rs.getString("UserEmailDB") + ", Finance: " + rs.getDouble("Finance"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading responsibles: " + e.getMessage());
        }
    }

    private static void updateResponsibleFinance(String userName, double newFinance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Responsible SET Finance = ? WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setDouble(1, newFinance);
            pst.setString(2, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Responsible finance updated successfully");
            } else {
                System.out.println("No responsible found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating responsible: " + e.getMessage());
        }
    }

    private static void deleteResponsible(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Responsible WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Responsible deleted successfully");
            } else {
                System.out.println("No responsible found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting responsible: " + e.getMessage());
        }
    }
}