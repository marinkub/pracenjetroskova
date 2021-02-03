package com.example.pracenjetroskova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.AdapterView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PovijestTroskova extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button button;
    String[] mjeseci = {"Svi mjeseci", "Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj", "Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac"};
    String[] mj = {"00","01","02","03","04","05","06","07","08","09","10","11","12"};
    RecyclerView recyclerView;
    TrošakAdapter trošakAdapter;
    List<Troškovi> ltroskovi;
    List<String> lkljucevi;
    String odabranimj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.povijest_troskova);

        button = (Button) findViewById(R.id.izlaz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriMainActivity();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.troskoviView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        ltroskovi = new ArrayList<>();
        lkljucevi = new ArrayList<>();


        Spinner spinner = (Spinner) findViewById(R.id.mjeseci);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mjeseci);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }


    public void otvoriMainActivity()
    {
        Intent intent = new Intent(this, PocetniIzbornik.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        odabranimj = mj[position];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (position != 0) {
            ltroskovi.clear();
            lkljucevi.clear();
            DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());
            myRef.child("troskovi").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Troškovi troškovi = ds.getValue(Troškovi.class);
                        String kljuc = ds.getKey();
                        String datum = ds.getValue(Troškovi.class).DajDatum();
                        String mj = datum.substring(3, 5);
                        if (mj.equals(odabranimj)) {
                            ltroskovi.add(troškovi);
                            lkljucevi.add(kljuc);
                        }
                    }
                    trošakAdapter = new TrošakAdapter(ltroskovi, lkljucevi);
                    recyclerView.setAdapter(trošakAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else
        {
            ltroskovi.clear();
            lkljucevi.clear();
            DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());
            myRef.child("troskovi").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Troškovi troškovi = ds.getValue(Troškovi.class);
                        String kljuc = ds.getKey();
                        ltroskovi.add(troškovi);
                        lkljucevi.add(kljuc);
                    }
                    trošakAdapter = new TrošakAdapter(ltroskovi, lkljucevi);
                    recyclerView.setAdapter(trošakAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}