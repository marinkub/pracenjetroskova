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

public class Prijava extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, lozinka;
    Button prijava;
    Button registracija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prijava);

        email = (EditText) findViewById(R.id.email_unos);
        lozinka = (EditText) findViewById(R.id.lozinka_unos);
        mAuth = FirebaseAuth.getInstance();
        prijava = (Button) findViewById(R.id.prijavabtn);
        registracija = (Button) findViewById(R.id.btnRegistracija1);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriRegistraciju();
            }
        });

        prijava.setOnClickListener(new View.OnClickListener() {
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

                mAuth.signInWithEmailAndPassword(Email, Lozinka).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), PocetniIzbornik.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Prijava.this, "Pogrešan E-mail ili lozinka", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    public void otvoriRegistraciju()
    {
        Intent intent = new Intent(this, Registracija.class);
        startActivity(intent);
    }
}