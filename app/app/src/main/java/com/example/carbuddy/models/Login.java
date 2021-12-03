package com.example.carbuddy.models;

public class Login {

    private String username, email, authkey;

    public Login(String authkey, String username, String email) {
        this.authkey = authkey;
        this.username = username;
        this.email = email;
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

    @Override
    public String toString() {
        return "Login{" +
                "authkey='" + authkey + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
