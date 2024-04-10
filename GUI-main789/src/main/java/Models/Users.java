package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Users {
    private StringProperty userNameDB;
    private StringProperty userEmailDB;

    public Users(String userNameDB, String userEmailDB) {
        this.userNameDB = new SimpleStringProperty(userNameDB);
        this.userEmailDB = new SimpleStringProperty(userEmailDB);
    }

    public String getUserNameDB() {
        return userNameDB.get();
    }

    public void setUserNameDB(String userNameDB) {
        this.userNameDB.set(userNameDB);
    }

    public StringProperty userNameDBProperty() {
        return userNameDB;
    }

    public String getUserEmailDB() {
        return userEmailDB.get();
    }

    public void setUserEmailDB(String userEmailDB) {
        this.userEmailDB.set(userEmailDB);
    }

    public StringProperty userEmailDBProperty() {
        return userEmailDB;
    }
}
