package com.example.weijinghusandoridlabs;

public class ChatMessage {
    public int id;
    private String message;
    private String timeSent;
    private boolean isSentButton;

    public ChatMessage(String m, String t, boolean sent) {
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
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
