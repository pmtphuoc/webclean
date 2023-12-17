package com.example.webcleand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.SanPham;

public class LuuSanPham extends AppCompatActivity {

    ArrayAdapter<SanPham>sanPhamAdapter;
    EditText ma;
    EditText ten;
    EditText gia;
    EditText madm;
    Button btnSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luu_san_pham);
        addControls();
        addEvents();
    }

    private void addControls() {
        ma=(EditText)findViewById(R.id.ma);
        ten=(EditText)findViewById(R.id.ten);
        gia=(EditText)findViewById(R.id.gia);
        madm=(EditText)findViewById(R.id.madm);
        btnSP =(Button) findViewById(R.id.btnSP) ;
        sanPhamAdapter = new ArrayAdapter<SanPham>(LuuSanPham.this, android.R.layout.simple_list_item_1);

    }

    private void addEvents() {

        btnSP.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                DanhSachSanPhamTask task = new DanhSachSanPhamTask();
                task.execute();
            }

        });

    }


    class DanhSachSanPhamTask extends AsyncTask<Void,Void, ArrayList<SanPham>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SanPham> sanPhams) {
            super.onPostExecute(sanPhams);
            sanPhamAdapter.clear();
            sanPhamAdapter.addAll(sanPhams);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<SanPham> doInBackground(Void... voids) {
            ArrayList<SanPham> dsSanPham = new ArrayList<>();
            try {
                String params = "?masp=" + ma.getText().toString() + "&tensp=" + ten.getText().toString() + "&dongia=" + gia.getText().toString() + "&madm=" + madm.getText().toString();
                URL url = new URL("http://192.168.1.6/webconga/api/sanpham/" + params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                //Lấy kq từ server
                InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append((line));

                }

                JSONArray jsArray = new JSONArray(builder.toString());
                for(int i=0; i<jsArray.length();i++){
                    JSONObject jsObject = jsArray.getJSONObject(i);
                    int masp = jsObject.getInt("Ma");
                    String tensp = jsObject.getString("Ten");
                    int dongia = jsObject.getInt("DonGia");
                    int madm = jsObject.getInt("MaDanhMuc");

                    SanPham sp = new SanPham();
                    sp.setMa(masp);
                    sp.setTen(tensp);
                    sp.setDonGia(dongia);
                    sp.setMaDanhMuc(Integer.toString(madm));
                    dsSanPham.add(sp);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e( "Error",ex.toString());
            }
            return dsSanPham;

        }

    }
}