package com.example.prason.biratclz.model;

/**
 * Created by Prason on 9/24/2017.
 */

public class AdminDataModel {
    String name, email, password,type;

//creating the constructor for the name and the type only because the email and password would have been saved in the
    //authenticatiion section and only the name and the type are required to be saved in the database part based on the uuserrID
    public AdminDataModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
