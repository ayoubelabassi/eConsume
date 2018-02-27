package com.ayoubtadakker.tadakker.checker.suivi;

import com.ayoubtadakker.tadakker.checker.compagne.User;

import java.util.Date;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class Consomation {
    private int id;
    private String name;
    private String description;
    private int qte;
    private double price;
    private Date date;

    public Consomation() {
    }

    public Consomation(int id, String name, String description, int qte, double price, Date date, int user_fk) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qte = qte;
        this.price = price;
        this.date = date;
        this.user_fk = user_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(int user_fk) {
        this.user_fk = user_fk;
    }

    private int user_fk;
}
