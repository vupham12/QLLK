package com.example.qllk;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
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

public class InsertLinhKien extends AppCompatActivity {
    SQLiteDatabase database;
    String DATABASE_NAME = "qllk.db";
    EditText txtID,txtTen, txtGia;
    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    ImageView imgAnhRemove;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_linh_kien);
        addControl();
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
                Intent intent = new Intent(InsertLinhKien.this,danhsachPk.class);
                startActivity(intent);

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
    }

    private void insert(){
        String id = txtID.getText().toString();
        String ten = txtTen.getText().toString();
        String gia = txtGia.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAnhRemove);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id" ,id);
        contentValues.put("ten",ten);
        contentValues.put("gia",gia);
        contentValues.put("Anh",anh);

        database = Database.initDatabase(InsertLinhKien.this, DATABASE_NAME);
        database.insert("linhkien", null, contentValues );

        Intent intent = new Intent(InsertLinhKien.this,danhsachPk.class);
        startActivity(intent);
    }
    //
    public byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



    private void addControl(){
        txtID = (EditText) findViewById(R.id.editTextMaNVThem);
        txtTen = (EditText) findViewById(R.id.editTextTenNVThem);
        txtGia = (EditText) findViewById(R.id.editTextSDTThem);
        btnChonHinh = (Button) findViewById(R.id.buttonChonHinhThem);
        btnChupHinh = (Button) findViewById(R.id.buttonChupHinhThem);
        btnLuu = (Button) findViewById(R.id.buttonLuuThem);
        btnHuy = (Button) findViewById(R.id.buttonHuyThem);
        imgAnhRemove = (ImageView) findViewById(R.id.imageViewAnhThem);


    }
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);

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

