package Models;

public class Performance {
    private int performanceID;
    private String userNameDB;
    private String userEmailDB;
    private double runsScored;
    private double ballsFaced;
    private double fours;
    private double sixes;
    private double wicketsTaken;
    private double ballsBowled;
    private double runsGave;

    public Performance() {}

    public Performance(int performanceID, String userNameDB, String userEmailDB, double runsScored, double ballsFaced, double fours, double sixes, double wicketsTaken, double ballsBowled, double runsGave) {
        this.performanceID = performanceID;
        this.userNameDB = userNameDB;
        this.userEmailDB = userEmailDB;
        this.runsScored = runsScored;
        this.ballsFaced = ballsFaced;
        this.fours = fours;
        this.sixes = sixes;
        this.wicketsTaken = wicketsTaken;
        this.ballsBowled = ballsBowled;
        this.runsGave = runsGave;
    }

    // Getters and setters
    public int getPerformanceID() {
        return performanceID;
    }

    public void setPerformanceID(int performanceID) {
        this.performanceID = performanceID;
    }

    public String getUserNameDB() {
        return userNameDB;
    }

    public void setUserNameDB(String userNameDB) {
        this.userNameDB = userNameDB;
    }

    public String getUserEmailDB() {
        return userEmailDB;
    }

    public void setUserEmailDB(String userEmailDB) {
        this.userEmailDB = userEmailDB;
    }

    public double getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(double runsScored) {
        this.runsScored = runsScored;
    }

    public double getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(double ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public double getFours() {
        return fours;
    }

    public void setFours(double fours) {
        this.fours = fours;
    }

    public double getSixes() {
        return sixes;
    }

    public void setSixes(double sixes) {
        this.sixes = sixes;
    }

    public double getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(double wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public double getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(double ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public double getRunsGave() {
        return runsGave;
    }

    public void setRunsGave(double runsGave) {
        this.runsGave = runsGave;
    }
}
