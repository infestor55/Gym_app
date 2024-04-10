package Models;

import java.util.Date;

public class Coach extends Users {
    private double salary;
    private String sport;
    private Date schedule;

    public Coach(String userNameDB, String userEmailDB, double salary, String sport, Date schedule) {
        super(userNameDB, userEmailDB);
        this.salary = salary;
        this.sport = sport;
        this.schedule = schedule;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }
}
