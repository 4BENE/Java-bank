package models.users;

import entities.bank.Bank;
import models.notification.Notification;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String name;
    public String surname;
    @Nullable public String address;
    @Nullable public String passport;
    @Nullable public List<Notification> allNotification;

    public User(String name, String surname, @Nullable String address, @Nullable String passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
    }
    public void subscribe(Bank bank){
        bank.notificationUsers.add(this);
        allNotification = new ArrayList<Notification>();
    }
    public String readNotification(Notification notification){
        return notification.getTheme() + '\n' + notification.getText();
    }
    public List<Notification> getAllNotification(){
        return allNotification;
    }
    public User addPassport(String passport){
        this.passport = passport;
        return this;
    }
    public User addAddress(String address){
        this.address = address;
        return this;
    }

    public boolean isVerified() {
        return this.address != null && this.passport != null;
    }
}
