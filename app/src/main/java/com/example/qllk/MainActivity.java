package com.example.qllk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtDN,edtPass;
    Button btnLg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDN = (EditText) findViewById(R.id.editTextDN);
        edtPass = (EditText) findViewById(R.id.editTextPass);
        btnLg = (Button) findViewById(R.id.buttonLg);

    btnLg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.buttonLg):
                    String userName = edtDN.getText().toString();
                    String pass = edtPass.getText().toString();
                    if (edtDN.getText().toString().equals("admin") && edtPass.getText().toString().equals("12345")){

                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    Intent ds = new Intent(MainActivity.this, danhsachPk.class);
                    //ds.putExtra("UserName:", edtDN.getText().toString());
                    //ds.putExtra("PassWord", edtPass.getText().toString());
                        startActivity(ds);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Đăng nhập không thành công!",Toast.LENGTH_SHORT).show();}
                    break;


        }
        }
    });
    }}


