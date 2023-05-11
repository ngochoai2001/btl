package com.example.btl.model;

public class ThongBao {
    String thongBao;
    String ngayGio;

    public ThongBao(String thongBao, String ngayGio) {
        this.thongBao = thongBao;
        this.ngayGio = ngayGio;
    }

    public String getThongBao() {
        return thongBao;
    }

    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }
}
