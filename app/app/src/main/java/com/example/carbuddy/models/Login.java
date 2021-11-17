package com.example.carbuddy.models;

public class Login {
    private boolean entrar;
    private String token;

    public Login(boolean entrar, String token) {
        this.entrar = entrar;
        this.token = token;
    }

    public boolean isEntrar() {
        return entrar;
    }

    public String getToken() {
        return token;
    }
}
