package com.example.menumaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddRestaurants extends AppCompatActivity {

    ImageView addImage;
    EditText name_rest,contact_rest,address_rest,landmark_rest,coordinates_rest;
    Button rest_Add;
    DBHelper DB2;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurants);

        name_rest = (EditText)findViewById(R.id.rst_name);
        contact_rest = (EditText)findViewById(R.id.rst_contact);
        address_rest = (EditText)findViewById(R.id.rst_address);
        landmark_rest = (EditText)findViewById(R.id.rst_landmark);
        coordinates_rest = (EditText)findViewById(R.id.rst_coordinates);
        rest_Add = (Button)findViewById(R.id.addRestaurant);
        addImage = (ImageView) findViewById(R.id.imgAdd);

        DB2 = new DBHelper(this);

        rest_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = name_rest.getText().toString();
                String ct = contact_rest.getText().toString();
                String ad = address_rest.getText().toString();
                String lm = landmark_rest.getText().toString();
                String co = coordinates_rest.getText().toString();

                if(nm.equals("") || ct.equals("") || ad.equals("") || lm.equals("") || co.equals("")){
                    Toast.makeText(AddRestaurants.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkrestaurant = DB2.checkRestaurant(ct);
                    if(checkrestaurant == false){
                        Boolean add = DB2.addRestaurant(nm,ct,ad,lm,co);
                        if(add == true){
                            Toast.makeText(AddRestaurants.this,"Restaurant added successfully.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Restaurants.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(AddRestaurants.this,"Restaurant adding failed.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AddRestaurants.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(AddRestaurants.this, "Restaurant already exists!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Restaurants.class);
                        startActivity(intent);
                    }
                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,PICK_IMAGE_REQUEST);
                }catch (Exception e){
                    Toast.makeText(AddRestaurants.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                addImage.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(AddRestaurants.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}