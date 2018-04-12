package com.example.prason.biratclz.model;

/**
 * Created by Prason on 7/22/2017.
 */

public class NotificationDataModule {
    private String notficationHeading;
    private String notificationContent;
    private String target_faculty;
    private String targe_semester;

    public NotificationDataModule(String notficationHeading, String notificationContent, String targe_semester, String target_faculty) {
        this.notficationHeading = notficationHeading;
        this.notificationContent = notificationContent;
        this.target_faculty = target_faculty;
        this.targe_semester = targe_semester;
    }

    public NotificationDataModule(String notficationHeading, String notificationContent) {
        this.notficationHeading = notficationHeading;
        this.notificationContent = notificationContent;
    }

    public String getTarget_faculty() {
        return target_faculty;
    }

    public String getTarge_semester() {
        return targe_semester;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public String getNotficationHeading() {

        return notficationHeading;
    }
}
