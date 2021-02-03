package com.example.pracenjetroskova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PocetnoStanje extends AppCompatActivity {
    public EditText stanje;
    public Button unesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetno_stanje);

        unesi = (Button) findViewById(R.id.btnPocetnoStanje);
        unesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                DatabaseReference myRef = database.getReference(auth.getCurrentUser().getUid());

                stanje = (EditText) findViewById(R.id.PocetnoStanje);


                myRef.child("stanje").setValue(stanje.getText().toString());

                Intent intent = new Intent(getApplicationContext(), PocetniIzbornik.class);
                startActivity(intent);
            }
        });

    }
}