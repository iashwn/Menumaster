package com.example.menumaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText name,email,phone,password;
    Button register;
    TextView toLogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.r_name);
        email = (EditText) findViewById(R.id.r_email);
        phone = (EditText) findViewById(R.id.r_phone);
        password = (EditText) findViewById(R.id.r_password);
        register = (Button) findViewById(R.id.b_reg);
        toLogin = (TextView) findViewById(R.id.logintv);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = name.getText().toString();
                String em = email.getText().toString();
                String no = phone.getText().toString();
                String pw = password.getText().toString();

                if(nm.equals("")||em.equals("")||no.equals("")||pw.equals("")){
                    Toast.makeText(Register.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuser = DB.checkEmail(em);
                    if(checkuser == false){
                        Boolean insert = DB.insertData(nm,em,no,pw);
                        if(insert == true){
                            Toast.makeText(Register.this,"Registration successful.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Register.this,"Registration failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Register.this, "User already exists! Please login.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                }

            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}