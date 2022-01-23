package com.example.carbuddy.listeners;

import com.example.carbuddy.models.Login;

/** Ficar à escuta para atualizar o Login **/
public interface LoginListener {
    void onValidateLogin(final Login login);
}

