package com.example.qllk;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
public class Adapterlinhkien extends BaseAdapter {
    Context context;
    ArrayList<linhkien> list;
    SQLiteDatabase database;
    String DATABASE_NAME = "qllk.db";
    TextView txtID,txtTenLK,txtgialk;
    ImageView imgAnh;
    Button btnSua,btnXoa;

    public Adapterlinhkien(Context context, ArrayList<linhkien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row,null);

        // Anh xa
        txtID =(TextView) row.findViewById(R.id.textViewMalk);
         txtTenLK =(TextView) row.findViewById(R.id.textViewTenlinhkien);
         txtgialk =(TextView) row.findViewById(R.id.textViewgialk);
         imgAnh =(ImageView) row.findViewById(R.id.imageAnh);
         btnSua =(Button) row.findViewById(R.id.buttonSua);
         btnXoa =(Button) row.findViewById(R.id.buttonXoa);

        linhkien linhKien = list.get(i);
        txtID.setText(linhKien.id+"");
        txtTenLK.setText(linhKien.tenlk);
        txtgialk.setText(linhKien.gialk);
        if(linhKien.anh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(linhKien.anh, 0, linhKien.anh.length);
            imgAnh.setImageBitmap(bitmap);
        }
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Update_activity.class);
                intent.putExtra("ID",linhKien.id);
                context.startActivity(intent);
            }
        });
        // Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xac nhan");
                builder.setMessage("Ban co muon xoa hay khong");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(linhKien.id);
                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });

        return row;

    }
    // Xữ lí sự kiện nút xóa
    private void delete(int maLK){
        database = Database.initDatabase((Activity) context, DATABASE_NAME);
        database.delete("linhkien", "id = ?",new String[] {maLK+""});

        Cursor cursor = database.rawQuery("Select * from linhkien ",null);
        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenlk = cursor.getString(1);
            String gia = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add( new linhkien(id,tenlk,gia,anh));

        }
        notifyDataSetChanged();
    }
}
