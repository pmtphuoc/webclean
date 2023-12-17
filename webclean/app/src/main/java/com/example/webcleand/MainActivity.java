package com.example.webcleand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moDanhSachSanPham(View view) {
        Intent intent=new Intent(MainActivity.this,DanhSachSanPham.class);
        startActivity(intent);
    }
    public void moDanhSachDanhMuc(View view) {
        Intent intent=new Intent(MainActivity.this,DanhSachDanhMuc.class);
        startActivity(intent);
    }
    public void moDanhMuc(View view) {
        Intent intent=new Intent(MainActivity.this,DanhMucID.class);
        startActivity(intent);

    }
    public void moDonGia(View view) {
        Intent intent=new Intent(MainActivity.this,SanPhamDonGia.class);
        startActivity(intent);

    }
    public void moLuu(View view) {
        Intent intent = new Intent(MainActivity.this, LuuSanPham.class);
        startActivity(intent);
    }
    public void moXoa(View view) {
        Intent intent = new Intent(MainActivity.this, Delete.class);
        startActivity(intent);
    }
    public void moSuasp(View view) {
        Intent intent = new Intent(MainActivity.this,SuaSP.class);
        startActivity(intent);
    }
    public void moSuadm(View view) {
        Intent intent = new Intent(MainActivity.this, SuaDM.class);
        startActivity(intent);
    }

}