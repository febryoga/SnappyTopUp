package com.example.asus.tugas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText pass , user;
    private Button Login;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( user.getText().toString().equals("user") && pass.getText().toString().equals("pass")){
                    Toast.makeText(Login.this, "Username and password is correct",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Admin.class);
                    startActivity(intent);

                }
                        else{
                    Toast.makeText(Login.this, "Username and password is NOT correct",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Tentang.class);
                startActivity(intent);
            }
        });

    }

    }

