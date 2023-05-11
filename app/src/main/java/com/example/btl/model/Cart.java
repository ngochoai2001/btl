package com.example.btl.model;

public class Cart {
    public int id;
    public String name;
    public int price;
    public  String avatar;
    public int SoLuong;

    public Cart(int id, String name, int price, String avatar, int soLuong) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.SoLuong = soLuong;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        this.SoLuong = soLuong;
    }
}
