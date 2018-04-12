package com.example.prason.biratclz.model;

import java.util.Date;

/**
 * Created by Prason on 10/26/2017.
 */

public class MessageDataModel {
    private String messageText;
    private String senderId;
    private String messageSender;
    private Long sentTimestamp;


    public MessageDataModel() {
    }

    public MessageDataModel(String messageText, String username, String senderId) {
        this.messageText = messageText;
        this.messageSender = username;
        this.sentTimestamp = new Date().getTime();
        this.senderId  = senderId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setUsername(String username) {
        this.messageSender = username;
    }

    public void setSentTimestamp(Long sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }

    public String getUsername() {
        return messageSender;
    }


    public String getMessageText() {
        return messageText;
    }



    public Long getSentTimestamp() {
        return sentTimestamp;
    }

}