package com.example.homework07;

import java.util.Date;

public class Chat {

    String sender, message;
    Date timestamp;

    public Chat(String sender, String message, Date timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Chat(){

    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
