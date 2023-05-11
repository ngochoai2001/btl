package com.example.btl.model;

import java.util.ArrayList;

public class HoaDon {

    String name;
    String phone;
    String adress;
    ArrayList<Cart> cartList;
    String tongTien;
    String ngayThang;

    public HoaDon(String name, String phone, String adress, ArrayList<Cart> cartList, String tongTien, String ngayThang) {
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.cartList = cartList;
        this.tongTien = tongTien;
        this.ngayThang = ngayThang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


    public ArrayList<Cart> getGioHangList() {
        return cartList;
    }

    public void setGioHangList(ArrayList<Cart> cartList) {
        this.cartList = cartList;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }
}
