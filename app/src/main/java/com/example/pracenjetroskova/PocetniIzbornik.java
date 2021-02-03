package com.example.pracenjetroskova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PocetniIzbornik extends AppCompatActivity {
    private Button UrediStanje;
    private Button UnesiTrosak;
    private Button Povijest;
    private Button Odjava;
    private TextView stanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetni_izbornik);

        stanje = (TextView) findViewById(R.id.stanje);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());


        myRef.child("stanje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                stanje.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PocetniIzbornik.this, "Greška", Toast.LENGTH_LONG).show();
            }
        });

        UrediStanje = (Button) findViewById(R.id.btnUrediStanje);
        UrediStanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriUrediStanje();
            }
        });

        UnesiTrosak = (Button) findViewById(R.id.btnTrosak);
        UnesiTrosak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriUnesiTrosak();
            }
        });

        Povijest = (Button) findViewById(R.id.btnPovijest);
        Povijest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriPovijestTroskova();
            }
        });

        Odjava = (Button) findViewById(R.id.btnOdjava);
        Odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odjava();
            }
        });
    }

    public void otvoriUrediStanje()
    {
        Intent intent = new Intent(this, UrediStanje.class);
        startActivity(intent);
    }

    public void otvoriUnesiTrosak()
    {
        Intent intent = new Intent(this, UnesiTrosak.class);
        startActivity(intent);
    }

    public void otvoriPovijestTroskova()
    {
        Intent intent = new Intent(this, PovijestTroskova.class);
        startActivity(intent);
    }

    public void odjava()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Prijava.class);
        startActivity(intent);
    }
}