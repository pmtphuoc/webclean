package com.example.webcleand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.DanhMuc;

public class DanhSachDanhMuc extends AppCompatActivity {
    ListView lvDanhMuc;
    ArrayAdapter<DanhMuc>danhMucAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_danh_muc  );
        addControls();
    }

    private void addControls() {
        lvDanhMuc = findViewById(R.id.lvDanhMuc);
        danhMucAdapter = new ArrayAdapter<DanhMuc>(DanhSachDanhMuc.this, android.R.layout.simple_list_item_1);
        lvDanhMuc.setAdapter(danhMucAdapter);
        //Gọi web services
        DanhSachSanPhamTask task = new DanhSachSanPhamTask();
        task.execute();
    }


    class DanhSachSanPhamTask extends AsyncTask<Void,Void, ArrayList<DanhMuc>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<DanhMuc> danhMucs) {
            super.onPostExecute(danhMucs);
            danhMucAdapter.clear();
            danhMucAdapter.addAll(danhMucs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<DanhMuc> doInBackground(Void... voids) {
            ArrayList<DanhMuc> dsDanhMuc = new ArrayList<>();
            try{
                URL url = new URL( "http://192.168.1.4/webconga/api/DanhMuc");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

                //Lấy kq từ server
                InputStreamReader isr =new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br =new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line =null;
                while ((line = br.readLine())!=null){
                    builder.append((line));

                }
                JSONArray jsArray = new JSONArray(builder.toString());
                for(int i=0; i<jsArray.length();i++){
                    JSONObject jsObject = jsArray.getJSONObject(i);
                    int madm = jsObject.getInt("Madanhmuc");
                    String tendm = jsObject.getString("Tendanhmuc");


                    DanhMuc dm = new DanhMuc();
                    dm.setMaDM(madm);
                    dm.setTenDM(tendm);
                    dsDanhMuc.add(dm);

                }
                br.close();
            }
            catch (Exception ex){
                Log.e( "Error",ex.toString());
            }
            return dsDanhMuc;

        }
    }
}