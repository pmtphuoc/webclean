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

import model.DanhMuc;


public class SuaDM extends AppCompatActivity {

    ArrayAdapter<DanhMuc>danhMucAdapter;
    EditText ma;
    EditText ten;

    Button btnDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_d_m);
        addControls();
        addEvents();
    }

    private void addControls() {
        ma=(EditText)findViewById(R.id.ma);
        ten=(EditText)findViewById(R.id.ten);

        btnDM =(Button) findViewById(R.id.btnDM) ;
        danhMucAdapter = new ArrayAdapter<DanhMuc>(SuaDM.this, android.R.layout.simple_list_item_1);

    }

    private void addEvents() {

        btnDM.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                DanhSachdmTask task = new DanhSachdmTask();
                task.execute();
            }

        });

    }


    class DanhSachdmTask extends AsyncTask<Void,Void, ArrayList<DanhMuc>> {
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
            try {
                String params="?masp="+ma.getText().toString()+"&tensp="+ten.getText().toString();
                URL url = new URL("http://192.168.1.6/webconga/api/sanpham/"+params);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
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
                    int madm = jsObject.getInt("Ma");
                    String tendm = jsObject.getString("Ten");


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