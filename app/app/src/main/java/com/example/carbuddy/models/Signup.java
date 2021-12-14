package com.example.carbuddy.models;

import com.example.carbuddy.utils.Json_Objects_Convertor;

public class Signup {
    String username;
    String email;
    String nif;
    String birsthday;
    String phonenumber;
    String password;

    public Signup(String username, String email, String nif, String birsthday, String phonenumber, String password) {
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.birsthday = birsthday;
        this.phonenumber = phonenumber;
        this.password = password;
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

    @Override
    public String toString() {
        return "Signup{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nif='" + nif + '\'' +
                ", birsthday='" + birsthday + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static boolean PasswordVerify(String password1, String password2){
        if(password1.matches(password2)){
            return true;
        }
        return false;
    }

    public void DoSignup(){
        Json_Objects_Convertor.sendPost("signup/post", Json_Objects_Convertor.jsonObjectConvert(this));
    }
}