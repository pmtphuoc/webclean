package model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int Ma;
    private String Ten;
    private int DonGia;
    private String MaDanhMuc;

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public String getMaDanhMuc() {
        return MaDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        MaDanhMuc = maDanhMuc;
    }

    @Override
    public String toString(){
        return Ma+"\n"+Ten+"\n"+DonGia+"\n";
    }
}
