package com.example.carbuddy.models;

import com.example.carbuddy.controllers.ApiCRUDActivity;

public class Signup {
    String username;
    String email;
    String nif;
    String birsthday;
    String phonenumber;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getBirsthday() {
        return birsthday;
    }

    public void setBirsthday(String birsthday) {
        this.birsthday = birsthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void DoSignup(){
        ApiCRUDActivity.sendPost("signup/post", ApiCRUDActivity.jsonObjectConvert(this));

    }
}