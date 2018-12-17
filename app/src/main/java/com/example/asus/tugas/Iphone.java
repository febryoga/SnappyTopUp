package com.example.asus.tugas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Iphone extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    String[] users = {"Nominal","10.000", "25.000", "50.000", "100.000", "150.000"};
    String [] atm = { "ATM", "BCA", "BNI" , "MANDIRI", "BRI" };
    Button btnSubmit;
    ImageView back , tentang;
    EditText name, email, phoneno;
    TextView result;
    Spinner spin , spin1 ;
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        tentang = findViewById(R.id.tentang);
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Iphone.this, Tentang.class);
                startActivity(intent);
            }
        });
        name = (EditText) findViewById(R.id.txtName);
        phoneno = (EditText) findViewById(R.id.txtPhone);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Iphone.this, MainActivity.class);
                startActivity(intent);
            }
        });

        spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        spin1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, atm);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);
        spin1.setOnItemSelectedListener(this);

        btnSubmit = (Button) findViewById(R.id.btnSend);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nominal = spin.getSelectedItem().toString();
                String atm = spin1.getSelectedItem().toString();

                Intent intent = new Intent(Iphone.this, Rincian2.class);
                intent.putExtra("name", name.getText());
                intent.putExtra("No Hp", phoneno.getText());
                intent.putExtra("Top Up", nominal );
                intent.putExtra("Via Atm", atm);

                startActivity(intent);
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }
}

