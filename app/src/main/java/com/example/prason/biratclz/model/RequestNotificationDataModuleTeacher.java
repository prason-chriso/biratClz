package com.example.prason.biratclz.model;

import java.util.Date;

/**
 * Created by Prason on 9/24/2017.
 */

public class RequestNotificationDataModuleTeacher {
    private String requestSenderNodeKey;
    private String requestSenderUsername;
    private String requestSenderUserEmail;
    private String requestSenderUserPass;
    private String requestSenderUserType;
    private String requestSenderUserAddress;
    private long requestSenderUserPhone;
    private long requestSenderTime;

    //DEFAULT CONSTRUCTOR
    public RequestNotificationDataModuleTeacher() {

    }

    public RequestNotificationDataModuleTeacher(String requestSenderNodeKey, String requestSenderUsername, String requestSenderUserEmail, String requestSenderUserPass, String requestSenderUserType, String requestSenderUserAddress, long requestSenderUserPhone) {
        this.requestSenderNodeKey = requestSenderNodeKey;
        this.requestSenderUsername = requestSenderUsername;
        this.requestSenderUserEmail = requestSenderUserEmail;
        this.requestSenderUserPass = requestSenderUserPass;
        this.requestSenderUserType = requestSenderUserType;
        this.requestSenderUserAddress = requestSenderUserAddress;
        this.requestSenderUserPhone = requestSenderUserPhone;
        this.requestSenderTime = new Date().getTime();

    }

    public String getRequestSenderNodeKey() {
        return requestSenderNodeKey;
    }

    public String getRequestSenderUsername() {
        return requestSenderUsername;
    }

    public String getRequestSenderUserEmail() {
        return requestSenderUserEmail;
    }

    public String getRequestSenderUserPass() {
        return requestSenderUserPass;
    }

    public String getRequestSenderUserType() {
        return requestSenderUserType;
    }

    public String getRequestSenderUserAddress() {
        return requestSenderUserAddress;
    }

    public long getRequestSenderUserPhone() {
        return requestSenderUserPhone;
    }

    public long getRequestSenderTime() {
        return requestSenderTime;
    }
}
