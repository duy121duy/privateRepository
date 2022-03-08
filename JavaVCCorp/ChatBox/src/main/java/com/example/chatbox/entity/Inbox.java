package com.example.chatbox.entity;

public class Inbox {
    private String message;
    private String username;
    private String usernameReceiver;

    public Inbox() {
    }

    public Inbox(String message, String username, String usernameReceiver) {
        this.message = message;
        this.username = username;
        this.usernameReceiver = usernameReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameReceiver() {
        return usernameReceiver;
    }

    public void setUsernameReceiver(String usernameReceiver) {
        this.usernameReceiver = usernameReceiver;
    }
}
