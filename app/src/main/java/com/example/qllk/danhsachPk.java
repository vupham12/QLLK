package com.example.qllk;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class danhsachPk extends AppCompatActivity {
    String DATABASE_NAME = "qllk.db";
    SQLiteDatabase database;
    ListView lstDSlk;
    ArrayList<linhkien> list;
    Adapterlinhkien adapterlinhkien;
    Button btnthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_pk);
        lstDSlk = (ListView) findViewById(R.id.listViewDSLK);
        btnthem = (Button) findViewById(R.id.btnThem);
        list = new ArrayList<>();
        adapterlinhkien = new Adapterlinhkien(danhsachPk.this,list);
        lstDSlk.setAdapter(adapterlinhkien);

        database = Database.initDatabase(danhsachPk.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from linhkien", null);
        //Hai
        for (int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int mabh = cursor.getInt(0);
            String tenbh = cursor.getString(1);
            String gia = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new linhkien(mabh,tenbh,gia,anh));
        }
        adapterlinhkien.notifyDataSetChanged();
        // Them
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(danhsachPk.this, InsertLinhKien.class);
                startActivity(intent);
            }
        });
    }
}
