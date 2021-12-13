package com.example.qllk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Update_activity extends AppCompatActivity {
    SQLiteDatabase database;
    String DATABASE_NAME = "qllk.db";
    EditText txtTenLK, txtGia,edtMa;
    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    ImageView imgAnhRemove;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int maLK;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activity);
        addControl();
        loadData();
        addEvent();
    }

    private void addEvent(){
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Update_activity.this,danhsachPk.class);
                startActivity(intent);

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }
    private void update(){
        // Lấy cơ sở dữ liệu từ AdapterLoaiCa
        String tenLK = txtTenLK.getText().toString();
        String gia = txtGia.getText().toString();
        String id = edtMa.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhRemove);

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLK",tenLK);
        contentValues.put("gia",gia);
        contentValues.put("ma",id);
        contentValues.put("Anh",anh);

        database = Database.initDatabase(Update_activity.this, DATABASE_NAME);
        database.update("linhkien", contentValues, "id = ?",new String[] {maLK+""});

        Intent intent = new Intent(Update_activity.this,danhsachPk.class);
        startActivity(intent);
    }
    public byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void loadData(){
        // lấy dữ liệu
        Intent intent = getIntent();
        maLK = intent.getIntExtra("ID", -1);
        if(maLK != -1){
            database = Database.initDatabase(Update_activity.this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("Select * from linhkien where id = ?", new String[] {maLK + ""} );
            cursor.moveToFirst();
            String tenLK = cursor.getString(1);
            String gia = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            if(anh != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
                imgAnhRemove.setImageBitmap(bitmap);

            }
            txtTenLK.setText(tenLK);
            txtGia.setText(gia);
            edtMa.setText(id);
        }
    }

    private void addControl(){
        txtTenLK = (EditText) findViewById(R.id.editTextTenLKUpdate);
        txtGia = (EditText) findViewById(R.id.editTextGiaUpdate);
        edtMa = (EditText)findViewById(R.id.editTextMalk) ;
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinh);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinh);
        btnLuu = (Button) findViewById(R.id.buttonLuu);
        btnHuy = (Button) findViewById(R.id.buttonHuy);
        imgAnhRemove = (ImageView) findViewById(R.id.imageAnhRemove);
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    // take photo and choose photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnhRemove.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhRemove.setImageBitmap(bitmap);
            }
        }
    }
}

