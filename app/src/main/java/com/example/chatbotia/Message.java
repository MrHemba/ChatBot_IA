package com.example.chatbotia;

public class Message {
    public static final int USER_MESSAGE = 0;
    public static final int CHATBOT_MESSAGE = 1;

    private String text;
    private int sender; // USER_MESSAGE o CHATBOT_MESSAGE

    public Message(String text, int sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public int getSender() {
        return sender;
    }
}
