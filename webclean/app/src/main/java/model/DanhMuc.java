package model;

import java.io.Serializable;

public class DanhMuc implements Serializable {
    public int getMaDM() {
        return MaDM;
    }

    public void setMaDM(int maDM) {
        MaDM = maDM;
    }

    public String getTenDM() {
        return TenDM;
    }

    public void setTenDM(String tenDM) {
        TenDM = tenDM;
    }

    private int MaDM ;
    private String TenDM;

    @Override
    public String toString() {
        return  MaDM +"\n"+
                 TenDM + "\n";
    }
}
