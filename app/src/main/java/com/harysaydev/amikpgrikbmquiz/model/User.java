package com.harysaydev.amikpgrikbmquiz.model;

/**
 * Created by harysay on 17/12/2017.
 */

public class User {
    private String Jeneng;
    private String Kunc;
    private String Mail;
    private String Gen;

    public User(String jeneng, String kunc, String mail, String gen) {
        Jeneng = jeneng;
        Kunc = kunc;
        Mail = mail;
        Gen = gen;
    }
    public  User(){

    }

    public String getJeneng() {
        return Jeneng;
    }

    public void setJeneng(String jeneng) {
        Jeneng = jeneng;
    }

    public String getKunc() {
        return Kunc;
    }

    public void setKunc(String kunc) {
        Kunc = kunc;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getGen() {
        return Gen;
    }

    public void setGen(String gen) {
        Gen = gen;
    }




}
