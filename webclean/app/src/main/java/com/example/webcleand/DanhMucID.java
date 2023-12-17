package com.example.webcleand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.SanPham;

public class  DanhMucID extends AppCompatActivity {
    ListView lvMaDM;
    ArrayAdapter<SanPham>sanPhamAdapter;
    EditText editText;
    Button btnDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymadanhmuc);
        addControls();
        addEvents();
    }

    private void addControls() {
        editText=(EditText)findViewById(R.id.DM);
        btnDM =(Button) findViewById(R.id.btnDM) ;
        lvMaDM = findViewById(R.id.lvMaDM);
        sanPhamAdapter = new ArrayAdapter<SanPham>(DanhMucID.this, android.R.layout.simple_list_item_1);
        lvMaDM.setAdapter(sanPhamAdapter);
    }

    private void addEvents() {

        btnDM.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                DanhSachSanPhamTask task = new DanhSachSanPhamTask();
                task.execute(editText.getText().toString());
            }

        });

    }

    /*public View getView(int position,View convertView){
        EditText editText=(EditText)findViewById(R.id.DM);
        editText.setText(position);
        Button btnDM =(Button) findViewById(R.id.btnDM) ;
        View rowView = findViewById(R.id.lvMaDM);

        return rowView ;
    }*/


//Fix String truyền url
    class DanhSachSanPhamTask extends AsyncTask<String,Void,ArrayList<SanPham>>{
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
        protected ArrayList<SanPham> doInBackground(String... strings) {
            ArrayList<SanPham> dsSanPham = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.6/webconga/api/sanpham/?madm="+strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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
                for(int i=0; i<jsArray.length();i++) {
                    JSONObject jsObject = jsArray.getJSONObject(i);
                    int masp = jsObject.getInt("Ma");
                    String tensp = jsObject.getString("Ten");
                    int dongia = jsObject.getInt("DonGia");
                    //int madm = jsObject.getInt("MaDanhMuc");

                    SanPham sp = new SanPham();
                    sp.setMa(masp);
                    sp.setTen(tensp);
                    sp.setDonGia(dongia);
                    //sp.setMaDanhMuc(madm);
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