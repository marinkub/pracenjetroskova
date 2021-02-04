package com.example.pracenjetroskova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UnesiTrosak extends AppCompatActivity {
    private Button unesiTrosak;
    private Button izlaz;
    private EditText uneseniTrosak;
    private TextView TrenutnoStanje;
    private EditText NazivTroska;
    private EditText OpisTroska;


    public String NovoStanje(String trosak, String stanje)
    {
        String Novostanje;
        Float fTrenutnoStanje = Float.parseFloat(stanje);
        Float fTrosak = Float.parseFloat(trosak);
        Float fNovoStanje = fTrenutnoStanje - fTrosak;
        Novostanje = String.valueOf(fNovoStanje);

        return Novostanje;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unesi_trosak);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());

        myRef.child("stanje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                TrenutnoStanje = (TextView) findViewById(R.id.textView5);
                TrenutnoStanje.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Button spremanje
        unesiTrosak = (Button) findViewById(R.id.unsei);
        unesiTrosak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpremiTrosak();
            }
        });

        //Button izlaz
        izlaz = (Button) findViewById(R.id.btnTrosakIzlaz);
        izlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriMainActivity();
            }
        });
    }

    public void SpremiTrosak()
    {
        TrenutnoStanje = (TextView) findViewById(R.id.textView5);
        uneseniTrosak = (EditText) findViewById(R.id.uneseniTrosak);
        if(TextUtils.isEmpty(uneseniTrosak.getText().toString()))
        {
            uneseniTrosak.setError("Niste unjeli trošak");
            return;
        }

        NazivTroska = (EditText) findViewById(R.id.NazivTroška);
        if(TextUtils.isEmpty(NazivTroska.getText().toString()))
        {
            NazivTroska.setError("Niste unjeli naziv troška");
            return;
        }
        OpisTroska = (EditText) findViewById(R.id.OpisTroška);
        if(TextUtils.isEmpty(OpisTroska.getText().toString()))
        {
            OpisTroska.setError("Niste unjeli opis troška");
            return;
        }
        Date datum = Calendar.getInstance().getTime();

        SimpleDateFormat datum_format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String novoStanje = NovoStanje(uneseniTrosak.getText().toString(), TrenutnoStanje.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());

        myRef.child("stanje").setValue(novoStanje);

        DatabaseReference myRed1 = database.getReference(auth.getCurrentUser().getUid());
        String key = myRed1.push().getKey();

        Troškovi trosak = new Troškovi(uneseniTrosak.getText().toString(), NazivTroska.getText().toString(), OpisTroska.getText().toString(),  datum_format.format(datum));
        myRed1.child("troskovi").child(key).setValue(trosak);

        Intent intent = new Intent(this, PocetniIzbornik.class);
        startActivity(intent);
    }

    public void otvoriMainActivity()
    {
        Intent intent = new Intent(this, PocetniIzbornik.class);
        startActivity(intent);
    }
}