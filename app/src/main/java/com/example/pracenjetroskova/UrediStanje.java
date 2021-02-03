package com.example.pracenjetroskova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UrediStanje extends AppCompatActivity {
    public Button Spremi;
    public Button Izlaz;
    public EditText stanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uredi_stanje);

        Spremi = (Button) findViewById(R.id.spremi);
        Spremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpremiNovoStanje();
            }
        });

        Izlaz = (Button) findViewById(R.id.btnStanjeIzlaz);
        Izlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtvoriMainActivity();
            }
        });
    }

    public void OtvoriMainActivity()
    {
        Intent intent = new Intent(this, PocetniIzbornik.class);
        startActivity(intent);
    }

    public void SpremiNovoStanje()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());

        stanje = (EditText) findViewById(R.id.NovoStanje);
        if(TextUtils.isEmpty(stanje.getText().toString()))
        {
            stanje.setError("Niste unjeli stanje");
            return;
        }


        myRef.child("stanje").setValue(stanje.getText().toString());

        Intent intent = new Intent(this, PocetniIzbornik.class);
        startActivity(intent);
    }
}