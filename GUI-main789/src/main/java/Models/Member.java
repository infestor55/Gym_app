package Models;

import java.util.Date;
import Models.Performance;

public class Member extends Users {
    private String membership;
    private int age;
    private double height;
    private double weight;
    private Date schedule;
    private Performance performance;

    public Member(String UserNameDB, String UserEmailDB, String membership, int age, double height, double weight, Date schedule, Performance performance) {
        super(UserNameDB, UserEmailDB);
        this.membership = membership;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.schedule = schedule;
        this.performance = performance;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }
}