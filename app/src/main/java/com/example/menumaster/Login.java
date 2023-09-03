package com.example.menumaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView toLogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.l_email);
        password = (EditText) findViewById(R.id.l_password);
        login = (Button) findViewById(R.id.b_log);
        toLogin = (TextView) findViewById(R.id.registertv);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String pw = password.getText().toString();

                if(em.equals("")||pw.equals("")){
                    Toast.makeText(Login.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = DB.checkEmailPassword(em,pw);
                    if(checkuserpass == true){

                        if(em.equals("admin@gmail.com") && pw.equals("password")){
                            Intent intent1 = new Intent(getApplicationContext(), AdminHome.class);
                            startActivity(intent1);
                            Toast.makeText(Login.this, "Admin login successful.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent2 = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(intent2);
                            Toast.makeText(Login.this, "User login successful.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(Login.this, "Login unsuccessful.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Register.class);
                startActivity(intent3);
            }
        });
    }
}