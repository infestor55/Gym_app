package Controllers;

import Models.Coach;
import Services.ServiceCoach;
import java.sql.SQLException;
import java.util.List;

public class CoachController {
    private final ServiceCoach serviceCoach;

    public CoachController() {
        this.serviceCoach = new ServiceCoach();
    }

    public void addCoach(Coach coach) {
        try {
            serviceCoach.add(coach);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoach(Coach coach) {
        try {
            serviceCoach.delete(coach);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCoach(Coach coach) {
        try {
            serviceCoach.update(coach);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Coach> getAllCoaches() {
        try {
            return serviceCoach.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
