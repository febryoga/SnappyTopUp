package com.example.asus.tugas;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Admin extends AppCompatActivity {
    private ImageView googleadmin, tentang , iphoneadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        googleadmin = findViewById(R.id.googleadmin);
        googleadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, data.class);
                startActivity(intent);
            }
        });

        tentang = findViewById(R.id.tentang);
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, Tentang.class);
                startActivity(intent);
            }
        });

        iphoneadmin = findViewById(R.id.iphoneadmin);
        iphoneadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, data2.class);
                startActivity(intent);
            }
        });



    }

}