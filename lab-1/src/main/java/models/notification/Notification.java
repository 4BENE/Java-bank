package models.notification;

import models.users.User;

public class Notification {
    private final String theme;
    private final String text;
    public Notification(String theme, String text){
        this.theme = theme;
        this.text = text;
    }
    public String getTheme(){
        return theme;
    }
    public String getText(){
        return text;
    }

}
