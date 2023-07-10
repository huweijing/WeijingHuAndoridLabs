package com.example.weijinghusandoridlabs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="message")
    private String message;
    @ColumnInfo(name="TimeSent")
    private String timeSent;
    @ColumnInfo(name="SendOrReceive")
    private boolean isSentButton;

    ChatMessage(){}

    public ChatMessage(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    ChatMessage(int id, String m, String t, boolean sent){
        this.id = id;
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String m) {
        this.message = m;
    }

    public String getTimeSent() {
        return timeSent;
    }
    public void setTimeSent(String t) {
        this.timeSent = t;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

    public void setSentButton(boolean sent) {
        isSentButton = sent;
    }
}
