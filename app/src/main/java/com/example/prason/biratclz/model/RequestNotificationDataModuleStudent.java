package com.example.prason.biratclz.model;

import java.util.Date;

/**
 * Created by Prason on 11/18/2017.
 */

public class RequestNotificationDataModuleStudent {
    private String requestSenderNodeKey;
    private String requestSenderUsername;
   private  String requestSenderUserEmail;
    private String requestSenderUserPass;
    private String requestSenderUserType;
    private String requestSenderUserAddress;
    private String requestSenderFaculty;
    private long requestSenderUserPhone;
    private long requestSenderTime;
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

    public String getRequestSenderFaculty() {
        return requestSenderFaculty;
    }

    public long getRequestSenderUserPhone() {
        return requestSenderUserPhone;
    }

    public long getRequestSenderTime() {
        return requestSenderTime;
    }

    public String getRequestSenderNodeKey() {
        return requestSenderNodeKey;
    }

    public RequestNotificationDataModuleStudent(String requestSenderNodeKey, String requestSenderUsername, String requestSenderUserEmail, String requestSenderUserPass, String requestSenderUserType, String requestSenderUserAddress, String requestSenderFaculty, long requestSenderUserPhone) {
        this.requestSenderNodeKey = requestSenderNodeKey;
        this.requestSenderUsername = requestSenderUsername;
        this.requestSenderUserEmail = requestSenderUserEmail;
        this.requestSenderUserPass = requestSenderUserPass;
        this.requestSenderUserType = requestSenderUserType;
        this.requestSenderUserAddress = requestSenderUserAddress;
        this.requestSenderFaculty = requestSenderFaculty;
        this.requestSenderUserPhone = requestSenderUserPhone;
        this.requestSenderTime = new Date().getTime();
    }

    public RequestNotificationDataModuleStudent() {
    }


}
