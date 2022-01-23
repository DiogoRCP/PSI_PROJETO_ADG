package com.example.carbuddy.models;

/** Modelo User, onde são definidos os getters, setters, construtores, propriedades e redefinição do método toString **/
public class User {
    private int id;
    private String username, userPassword, userType, nif, birthday, phoneNumber, registrationDate;

    /** Construtor do user **/
    public User(int id, String username, String userPassword, String userType, String nif, String birthday, String phoneNumber, String registrationDate) {
        this.id = id;
        this.username = username;
        this.userPassword = userPassword;
        this.userType = userType;
        this.nif = nif;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /** Redefinição do método toString **/
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", nif='" + nif + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
