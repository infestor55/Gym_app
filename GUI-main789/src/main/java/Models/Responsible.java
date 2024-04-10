package Models;

import java.util.Date;

public class Responsible extends Users {
    private double finance;

    public Responsible(String UserNameDB, String UserEmailDB, double finance) {
        super(UserNameDB, UserEmailDB);
        this.finance = finance;
    }

    public double getFinance() {
        return finance;
    }

    public void setFinance(double finance) {
        this.finance = finance;
    }
}