package com.example.asus.tugas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread thread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(2000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }

    };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();     }
}
