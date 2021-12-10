package com.example.carbuddy.models;

import android.widget.DatePicker;

public class Login {

    private String username, email, authkey, nif, phonenumber, birsthday;

    public Login(String authkey, String username, String email, String nif, String phonenumber, String birsthday) {
        this.authkey = authkey;
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.phonenumber = phonenumber;
        this.birsthday = birsthday;
    }


    public String getToken() {
        return authkey;
    }

    public void setToken(String token) {
        this.authkey = token;
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
                ", authkey='" + authkey + '\'' +
                ", nif='" + nif + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", birsthday=" + birsthday +
                '}';
    }
}
