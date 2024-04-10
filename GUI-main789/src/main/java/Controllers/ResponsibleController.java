package Controllers;

import Models.Responsible;
import Services.ServiceResponsible;
import java.sql.SQLException;
import java.util.List;

public class ResponsibleController {
    private final ServiceResponsible serviceResponsible;

    public ResponsibleController() {
        this.serviceResponsible = new ServiceResponsible();
    }

    public void addResponsible(Responsible responsible) {
        try {
            serviceResponsible.add(responsible);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResponsible(Responsible responsible) {
        try {
            serviceResponsible.delete(responsible);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateResponsible(Responsible responsible) {
        try {
            serviceResponsible.update(responsible);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Responsible> getAllResponsibles() {
        try {
            return serviceResponsible.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
