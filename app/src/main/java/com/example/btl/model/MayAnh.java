package com.example.btl.model;

import java.io.Serializable;

public class MayAnh implements Serializable {
    int id;
    String brand;
    String name;
    int price;
    int amount;
    String avatar;

    public MayAnh(int id, String brand, String name, int price, int amount, String avatar) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

