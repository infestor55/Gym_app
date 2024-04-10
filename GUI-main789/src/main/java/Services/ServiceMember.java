package Services;

import Models.Member;
import Models.Performance;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceMember implements IService<Member> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        String userEmail = member.getUserEmailDB();
        String membership = member.getMembership();
        int age = member.getAge();
        double height = member.getHeight();
        double weight = member.getWeight();
        Date schedule = (Date) member.getSchedule();
        createMember(userName, userEmail, membership, age, height, weight, schedule);
    }

    @Override
    public void delete(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        deleteMember(userName);
    }

    @Override
    public void update(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        String newMembership = member.getMembership();
        int newAge = member.getAge();
        double newHeight = member.getHeight();
        double newWeight = member.getWeight();
        Date newSchedule = (Date) member.getSchedule();
        updateMemberInfo(userName, newMembership, newAge, newHeight, newWeight, newSchedule);
    }

    @Override
    public List<Member> readAll() throws SQLException {
        List<Member> members = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Member");
            while (rs.next()) {
                String userName = rs.getString("UserNameDB");
                String email = rs.getString("UserEmailDB");
                String membership = rs.getString("Membership");
                int age = rs.getInt("Age");
                double height = rs.getDouble("Height");
                double weight = rs.getDouble("Weight");
                Date schedule = rs.getTimestamp("Schedule");
                members.add(new Member(userName, email, membership, age, height, weight, schedule, new Performance(1, userName, email, 100, 50, 10, 5, 2, 60, 70)));
            }
        }
        return members;
    }


    @Override
    public Member findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createMember(String userName, String userEmail, String membership, int age, double height, double weight, Date schedule) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Member (UserNameDB, UserEmailDB, Membership, Age, Height, Weight, Schedule) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setString(2, userEmail);
            pst.setString(3, membership);
            pst.setInt(4, age);
            pst.setDouble(5, height);
            pst.setDouble(6, weight);
            pst.setTimestamp(7, new Timestamp(schedule.getTime()));
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Member added successfully");
            } else {
                System.out.println("Failed to add member");
            }
        } catch (SQLException e) {
            System.err.println("Error adding member: " + e.getMessage());
        }
    }

    private static void readMembers() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Member";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Username: " + rs.getString("UserNameDB") + ", Email: " + rs.getString("UserEmailDB") + ", Membership: " + rs.getString("Membership") + ", Age: " + rs.getInt("Age") + ", Height: " + rs.getDouble("Height") + ", Weight: " + rs.getDouble("Weight") + ", Schedule: " + rs.getTimestamp("Schedule").toLocalDateTime());
            }
        } catch (SQLException e) {
            System.err.println("Error reading members: " + e.getMessage());
        }
    }

    private static void updateMemberInfo(String userName, String newMembership, int newAge, double newHeight, double newWeight, Date newSchedule) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Member SET Membership = ?, Age = ?, Height = ?, Weight = ?, Schedule = ? WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, newMembership);
            pst.setInt(2, newAge);
            pst.setDouble(3, newHeight);
            pst.setDouble(4, newWeight);
            pst.setTimestamp(5, new Timestamp(newSchedule.getTime()));
            pst.setString(6, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Member information updated successfully");
            } else {
                System.out.println("No member found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating member information: " + e.getMessage());
        }
    }

    private static void deleteMember(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Member WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Member deleted successfully");
            } else {
                System.out.println("No member found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
        }
    }

}
