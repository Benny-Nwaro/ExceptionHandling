package com.example.lms.chat;

import java.util.UUID;

public class ChatMessage {
    private UUID senderId;
    private String senderName;
    private String content;
    private Type type; // Enum instead of String
    private String profileImageUrl; // Ensure this field is included


    public ChatMessage() {}

    public ChatMessage(UUID senderId,String senderName, String content, Type type, String profileImageUrl) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.type = type;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters
    public UUID getSender() { return senderId; }
    public void setSender(UUID sender) { this.senderId = sender; }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
}
