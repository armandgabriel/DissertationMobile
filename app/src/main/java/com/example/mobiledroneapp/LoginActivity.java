package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.SHAUtils;
import com.example.mobiledroneapp.helpers.ToastHelper;
import com.example.mobiledroneapp.models.Owner;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;

    Button loginButton;

    EditText userEditText;
    EditText passEditText;

    DBHelperAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = this;

        registerButton = findViewById(R.id.btnRegister);
        loginButton = findViewById(R.id.btnLogin);
        userEditText = (EditText) findViewById(R.id.inpUser);
        passEditText = (EditText) findViewById(R.id.inpPassword);

        dbAdapter = new DBHelperAdapter(context);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userEditText.getText().toString();
                String pass = passEditText.getText().toString();
                Toast.makeText(context,
                        user.toString() + " " + pass.toString(),
                        Toast.LENGTH_SHORT).show();
                Owner owner = dbAdapter.getOwner(user);
                if(user.equals(owner.getEmail()) && !SHAUtils.bin2hex(SHAUtils.getHash(pass)).equals(owner.getPassword())) {
                    ToastHelper.message(context, "Not Approved!");
                } else {
                    dbAdapter.updateLastLogin(owner.getId());
                    Intent intent = new Intent(context, HomeScreen.class);
                    startActivity(intent);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Register.class);
                startActivity(intent);
            }
        });
    }
}