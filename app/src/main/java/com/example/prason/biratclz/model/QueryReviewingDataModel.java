package com.example.prason.biratclz.model;

import java.util.Date;

/**
 * Created by Prason on 11/17/2017.
 */

public class QueryReviewingDataModel {
    private String queryText;
    private String senderMailID;
    private long sentTime;
    private String querySubject;

    public QueryReviewingDataModel(String queryText, String senderMailID, String querySubject) {
        this.queryText = queryText;
        this.senderMailID = senderMailID;
        this.sentTime = new Date().getTime();
        this.querySubject = querySubject;
    }

    QueryReviewingDataModel() {
        //default constructor;
    }

    //this constructor is used while retriving the data from the database for notification generating
    public QueryReviewingDataModel(String queryText, String sender, String subject, long sentTime) {
        this.queryText = queryText;
        this.senderMailID = sender;
        this.sentTime = sentTime;
        this.querySubject = subject;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getSenderMailID() {
        return senderMailID;
    }

    public void setSenderMailID(String senderMailID) {
        this.senderMailID = senderMailID;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public String getQuerySubject() {
        return querySubject;
    }

    public void setQuerySubject(String querySubject) {
        this.querySubject = querySubject;
    }
}
