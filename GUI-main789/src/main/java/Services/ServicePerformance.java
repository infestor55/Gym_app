package Services;

import Models.Performance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePerformance implements IService<Performance> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Performance performance) throws SQLException {
        createPerformance(performance);
    }

    @Override
    public void delete(Performance performance) throws SQLException {
        deletePerformance(performance.getPerformanceID());
    }

    @Override
    public void update(Performance performance) throws SQLException {
        updatePerformance(performance);
    }

    @Override
    public List<Performance> readAll() throws SQLException {
        return readPerformances();
    }

    @Override
    public Performance findbyId(int id) throws SQLException {
        return findPerformanceById(id);
    }

    private static void createPerformance(Performance performance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Performance (UserNameDB, UserEmailDB, RunsScored, BallsFaced, Fours, Sixes, WicketsTaken, BallsBowled, RunsGave) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, performance.getUserNameDB());
            pst.setString(2, performance.getUserEmailDB());
            pst.setDouble(3, performance.getRunsScored());
            pst.setDouble(4, performance.getBallsFaced());
            pst.setDouble(5, performance.getFours());
            pst.setDouble(6, performance.getSixes());
            pst.setDouble(7, performance.getWicketsTaken());
            pst.setDouble(8, performance.getBallsBowled());
            pst.setDouble(9, performance.getRunsGave());
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Performance added successfully");
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    performance.setPerformanceID(generatedKeys.getInt(1));
                }
            } else {
                System.out.println("Failed to add performance");
            }
        } catch (SQLException e) {
            System.err.println("Error adding performance: " + e.getMessage());
        }
    }

    private static List<Performance> readPerformances() {
        List<Performance> performances = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Performance";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                Performance performance = new Performance(
                        rs.getInt("PerformanceID"),
                        rs.getString("UserNameDB"),
                        rs.getString("UserEmailDB"),
                        rs.getDouble("RunsScored"),
                        rs.getDouble("BallsFaced"),
                        rs.getDouble("Fours"),
                        rs.getDouble("Sixes"),
                        rs.getDouble("WicketsTaken"),
                        rs.getDouble("BallsBowled"),
                        rs.getDouble("RunsGave")
                );
                performances.add(performance);
            }
        } catch (SQLException e) {
            System.err.println("Error reading performances: " + e.getMessage());
        }
        return performances;
    }

    private static void updatePerformance(Performance performance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Performance SET UserNameDB = ?, UserEmailDB = ?, RunsScored = ?, BallsFaced = ?, Fours = ?, Sixes = ?, WicketsTaken = ?, BallsBowled = ?, RunsGave = ? WHERE PerformanceID = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, performance.getUserNameDB());
            pst.setString(2, performance.getUserEmailDB());
            pst.setDouble(3, performance.getRunsScored());
            pst.setDouble(4, performance.getBallsFaced());
            pst.setDouble(5, performance.getFours());
            pst.setDouble(6, performance.getSixes());
            pst.setDouble(7, performance.getWicketsTaken());
            pst.setDouble(8, performance.getBallsBowled());
            pst.setDouble(9, performance.getRunsGave());
            pst.setInt(10, performance.getPerformanceID());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Performance updated successfully");
            } else {
                System.out.println("No performance found for the given ID");
            }
        } catch (SQLException e) {
            System.err.println("Error updating performance: " + e.getMessage());
        }
    }

    private static void deletePerformance(int performanceID) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Performance WHERE PerformanceID = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setInt(1, performanceID);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Performance deleted successfully");
            } else {
                System.out.println("No performance found for the given ID");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting performance: " + e.getMessage());
        }
    }

    private static Performance findPerformanceById(int performanceID) {
        Performance performance = null;
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String selectQuery = "SELECT * FROM Performance WHERE PerformanceID = ?";
            PreparedStatement pst = con.prepareStatement(selectQuery);
            pst.setInt(1, performanceID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                performance = new Performance(
                        rs.getInt("PerformanceID"),
                        rs.getString("UserNameDB"),
                        rs.getString("UserEmailDB"),
                        rs.getDouble("RunsScored"),
                        rs.getDouble("BallsFaced"),
                        rs.getDouble("Fours"),
                        rs.getDouble("Sixes"),
                        rs.getDouble("WicketsTaken"),
                        rs.getDouble("BallsBowled"),
                        rs.getDouble("RunsGave")
                );
            } else {
                System.out.println("No performance found for the given ID");
            }
        } catch (SQLException e) {
            System.err.println("Error finding performance: " + e.getMessage());
        }
        return performance;
    }
}
