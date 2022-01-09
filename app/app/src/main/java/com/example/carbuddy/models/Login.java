package com.example.carbuddy.models;

import android.widget.DatePicker;

public class Login {

    private int id;
    private String username, email, auth_key, nif, phonenumber, birsthday;

    public Login(int id, String auth_key, String username, String email, String nif, String phonenumber, String birsthday) {
        this.id = id;
        this.auth_key = auth_key;
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.phonenumber = phonenumber;
        this.birsthday = birsthday;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return auth_key;
    }

    public void setToken(String token) {
        this.auth_key = token;
    }

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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBirsthday() {
        return birsthday;
    }

    public void setBirsthday(String birsthday) {
        this.birsthday = birsthday;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", authkey='" + auth_key + '\'' +
                ", nif='" + nif + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", birsthday=" + birsthday +
                '}';
    }
}
