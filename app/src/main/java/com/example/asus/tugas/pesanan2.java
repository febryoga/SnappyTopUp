package com.example.asus.tugas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class pesanan2 extends AppCompatActivity {

    private TextView email,nohp,topup;
    private ImageView gambar, back;
    private  String id;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan2);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pesanan2.this, data2.class);
                startActivity(intent);
            }
        });

        id = getIntent().getExtras().get("id").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Iphone").child(id);
        email = findViewById(R.id.epesanan);
        nohp = findViewById(R.id.enohp);
        topup = findViewById(R.id.etopup);
        gambar = findViewById(R.id.egambar);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String emailstr = dataSnapshot.child("Email").getValue().toString();
                    String nohpstr = dataSnapshot.child("No Hp").getValue().toString();
                    String topupstr = dataSnapshot.child("Top Up").getValue().toString();
                    String gambar1str = dataSnapshot.child("gambar1").getValue().toString();

                    email.setText(emailstr);
                    nohp.setText(nohpstr);
                    topup.setText(topupstr);

                    Picasso.get().load(gambar1str).into(gambar);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
