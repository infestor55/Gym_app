package Services;

import Models.Coach;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCoach implements IService<Coach> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Coach coach) throws SQLException {
        createCoach(coach);
    }

    @Override
    public void delete(Coach coach) throws SQLException {
        deleteCoach(coach.getUserNameDB());
    }

    @Override
    public void update(Coach coach) throws SQLException {
        updateCoach(coach);
    }

    @Override
    public List<Coach> readAll() throws SQLException {
        return readCoaches();
    }

    @Override
    public Coach findbyId(int id) throws SQLException {
        return null;
    }

    private static void createCoach(Coach coach) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Coach (UserNameDB, UserEmailDB, Salary, Sport, Schedule) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, coach.getUserNameDB());
            pst.setString(2, coach.getUserEmailDB());
            pst.setDouble(3, coach.getSalary());
            pst.setString(4, coach.getSport());
            pst.setTimestamp(5, new Timestamp(coach.getSchedule().getTime()));
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Coach added successfully");
            } else {
                System.out.println("Failed to add coach");
            }
        } catch (SQLException e) {
            System.err.println("Error adding coach: " + e.getMessage());
        }
    }

    private static List<Coach> readCoaches() {
        List<Coach> coaches = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Coach";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getString("UserNameDB"),
                        rs.getString("UserEmailDB"),
                        rs.getDouble("Salary"),
                        rs.getString("Sport"),
                        rs.getTimestamp("Schedule")
                );
                coaches.add(coach);
            }
        } catch (SQLException e) {
            System.err.println("Error reading coaches: " + e.getMessage());
        }
        return coaches;
    }

    private static void updateCoach(Coach coach) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Coach SET UserEmailDB = ?, Salary = ?, Sport = ?, Schedule = ? WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, coach.getUserEmailDB());
            pst.setDouble(2, coach.getSalary());
            pst.setString(3, coach.getSport());
            pst.setTimestamp(4, new Timestamp(coach.getSchedule().getTime()));
            pst.setString(5, coach.getUserNameDB());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Coach information updated successfully");
            } else {
                System.out.println("No coach found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating coach information: " + e.getMessage());
        }
    }

    private static void deleteCoach(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Coach WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Coach deleted successfully");
            } else {
                System.out.println("No coach found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting coach: " + e.getMessage());
        }
    }
}
