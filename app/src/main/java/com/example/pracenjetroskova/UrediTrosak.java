package com.example.pracenjetroskova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class UrediTrosak extends AppCompatActivity {

    private Button Unsi;
    private Button Izlaz;
    private EditText uneseniTrosak;
    private TextView TrenutnoStanje;
    private EditText NazivTroska;
    private EditText OpisTroska;
    String staritrosak;
    String kljuc;


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
        setContentView(R.layout.uredi_trosak);

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
        Unsi = (Button) findViewById(R.id.unsei);
        Unsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrediTrosak();
            }
        });

        //Button izlaz
        Izlaz = (Button) findViewById(R.id.btnTrosakIzlaz);
        Izlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriPovijest();
            }
        });

        Intent intent = getIntent();
        kljuc = intent.getStringExtra("kljuc");
        String naziv = intent.getStringExtra("naziv");
        String opis = intent.getStringExtra("opis");
        staritrosak = intent.getStringExtra("ftrosak");
        NazivTroska = (EditText) findViewById(R.id.NazivTroška);
        uneseniTrosak = (EditText) findViewById(R.id.uneseniTrosak);
        OpisTroska = (EditText) findViewById(R.id.OpisTroška);
        NazivTroska.setText(naziv);
        OpisTroska.setText(opis);
        uneseniTrosak.setText(staritrosak);
    }

    public void UrediTrosak()
    {
        TrenutnoStanje = (TextView) findViewById(R.id.textView5);
        uneseniTrosak = (EditText) findViewById(R.id.uneseniTrosak);
        NazivTroska = (EditText) findViewById(R.id.NazivTroška);
        OpisTroska = (EditText) findViewById(R.id.OpisTroška);

        Float trenutnoStanje = Float.parseFloat(TrenutnoStanje.getText().toString());
        Float fStariTrosak = Float.parseFloat(staritrosak);
        Float fnoviTrosak = Float.parseFloat(uneseniTrosak.getText().toString());
        if(fStariTrosak < fnoviTrosak)
        {
            String novoStanje;
            Float razlika = fnoviTrosak - fStariTrosak;
            Float fNovoStanje = trenutnoStanje - razlika;
            novoStanje = fNovoStanje.toString();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid()).child("stanje");

            myRef.setValue(novoStanje);

            DatabaseReference myRef2 = database.getReference(auth.getCurrentUser().getUid()).child("troskovi");
            myRef2.child(kljuc).child("naziv").setValue(NazivTroska.getText().toString());
            myRef2.child(kljuc).child("opis").setValue(OpisTroska.getText().toString());
            myRef2.child(kljuc).child("trosak").setValue(fnoviTrosak.toString());
            otvoriPovijest();
        }
        if (fStariTrosak > fnoviTrosak)
        {
            String novoStanje;
            Float razlika = fStariTrosak - fnoviTrosak;
            Float fNovoStanje = trenutnoStanje + razlika;
            novoStanje = fNovoStanje.toString();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid()).child("stanje");

            myRef.setValue(novoStanje);

            DatabaseReference myRef2 = database.getReference(auth.getCurrentUser().getUid()).child("troskovi");
            myRef2.child(kljuc).child("naziv").setValue(NazivTroska.getText().toString());
            myRef2.child(kljuc).child("opis").setValue(OpisTroska.getText().toString());
            myRef2.child(kljuc).child("trosak").setValue(fnoviTrosak.toString());
            otvoriPovijest();
        }
        if (fStariTrosak.equals(fnoviTrosak))
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            DatabaseReference myRef2 = database.getReference(auth.getCurrentUser().getUid()).child("troskovi");
            myRef2.child(kljuc).child("naziv").setValue(NazivTroska.getText().toString());
            myRef2.child(kljuc).child("opis").setValue(OpisTroska.getText().toString());
            otvoriPovijest();
        }
    }

    public void otvoriPovijest()
    {
        Intent intent = new Intent(this, PovijestTroskova.class);
        startActivity(intent);
    }
}