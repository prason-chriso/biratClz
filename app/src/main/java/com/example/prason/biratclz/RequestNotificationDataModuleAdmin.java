package com.example.prason.biratclz;

import java.util.Date;

/**
 * Created by Prason on 12/5/2017.
 */

public class RequestNotificationDataModuleAdmin {
    private String requestSenderNodeKey;
    private String requestSenderUsername;
    private  String requestSenderUserEmail;
    private String requestSenderUserPass;
    private String requestSenderUserType;
    private long requestSenderTime;



    RequestNotificationDataModuleAdmin(){

    }


    public RequestNotificationDataModuleAdmin(String requestSenderNodeKey, String requestSenderUsername, String requestSenderUserEmail, String requestSenderUserPass, String requestSenderUserType) {
        this.requestSenderNodeKey = requestSenderNodeKey;
        this.requestSenderUsername = requestSenderUsername;
        this.requestSenderUserEmail = requestSenderUserEmail;
        this.requestSenderUserPass = requestSenderUserPass;
        this.requestSenderUserType = requestSenderUserType;
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

    public long getRequestSenderTime() {
        return requestSenderTime;
    }
}
