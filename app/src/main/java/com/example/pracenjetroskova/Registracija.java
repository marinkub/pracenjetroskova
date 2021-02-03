package com.example.pracenjetroskova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registracija extends AppCompatActivity {
    private Button izlaz;
    private EditText email, lozinka;
    private FirebaseAuth auth;
    private Button registracija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija);

        email = (EditText) findViewById(R.id.email_unos_reg);
        lozinka = (EditText) findViewById(R.id.lozinka_unos_reg);

        auth = FirebaseAuth.getInstance();

        registracija = (Button) findViewById(R.id.btnRegistracija2);
        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Lozinka = lozinka.getText().toString();

                if(TextUtils.isEmpty(Email))
                {
                    email.setError("Niste unjeli E-mail");
                    return;
                }

                if(TextUtils.isEmpty(Lozinka))
                {
                    lozinka.setError("Niste unjeli lozinku");
                    return;
                }

                if (Lozinka.length() < 6)
                {
                    lozinka.setError("Lozinka treba imati 6 ili više znakova");
                    return;
                }

                auth.createUserWithEmailAndPassword(Email, Lozinka).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Registracija.this, "Korisnik kreiran", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), PocetnoStanje.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Registracija.this, "Greška", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        izlaz = (Button) findViewById(R.id.btnRegistracijaIzlaz);
        izlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Izlaz();
            }
        });
    }

    public void Izlaz()
    {
        Intent intent = new Intent(this, Prijava.class);
        startActivity(intent);
    }
}