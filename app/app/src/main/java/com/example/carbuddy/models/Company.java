package com.example.carbuddy.models;

/** Modelo Company, onde são definidos os getters, setters, construtores, propriedades e redefinição do método toString **/
public class Company {
    private int id;
    private String companyname, nif, email, phonenumber, registrationdate;

    /** Construtor da company **/
    public Company(int id, String companyname, String nif, String email, String phonenumber, String registrationdate) {
        this.id = id;
        this.companyname = companyname;
        this.nif = nif;
        this.email = email;
        this.phonenumber = phonenumber;
        this.registrationdate = registrationdate;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyname;
    }

    public void setCompanyName(String companyName) {
        this.companyname = companyname;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phonenumber = phonenumber;
    }

    public String getRegistrationDate() {
        return registrationdate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationdate = registrationdate;
    }

    /** Redefinição do método toString **/
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyname + '\'' +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phonenumber + '\'' +
                ", registrationDate='" + registrationdate + '\'' +
                '}';
    }
}
