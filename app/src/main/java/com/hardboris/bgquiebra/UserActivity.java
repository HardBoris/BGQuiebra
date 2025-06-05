package com.hardboris.bgquiebra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    EditText user, credenciada;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user = findViewById(R.id.txtUser);
        credenciada = findViewById(R.id.txtCredenciada);

        ok = findViewById(R.id.btnOk);

        ok.setOnClickListener(v -> {
            Bundle enviaDatos = new Bundle();
            enviaDatos.putString("d1", user.getText().toString());
            enviaDatos.putString("d2", credenciada.getText().toString());
            Intent principal = new Intent(UserActivity.this, MainActivity.class);
            principal.putExtras(enviaDatos);
            startActivity(principal);
        });

    }
}