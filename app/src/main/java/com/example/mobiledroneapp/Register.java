package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.SHAUtils;
import com.example.mobiledroneapp.helpers.ToastHelper;
import com.example.mobiledroneapp.models.Owner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity {

    Button registerNow;
    EditText    userName;
    EditText    password;
    EditText    email;

    DBHelperAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerNow = (Button)findViewById(R.id.btnRegisterNow);
        userName = (EditText) findViewById(R.id.txtUserNameRegister);
        password = (EditText) findViewById(R.id.txtPasswordRegister);
        email = (EditText) findViewById(R.id.txtEmailRegister);

        final Context context = this;

        // DBHelper
        dbAdapter = new DBHelperAdapter(context);

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String _email = email.getText().toString();
                String pass = password.getText().toString();
                ToastHelper.message(getApplicationContext(), user + " " + _email + " " + pass);

                String timeFormat = "yyyy-mm-dd HH:MM:SS.SSS";
                Date dateNow = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat(timeFormat);

                String _creationDate = dateFormat.format(dateNow);
                String _lastLoginDate = dateFormat.format(dateNow);
                String home = "32.23#33.44";
                Owner owner = new Owner();
                owner.setName(user);
                owner.setEmail(_email);
                owner.setPassword(SHAUtils.bin2hex(SHAUtils.getHash(pass)));
                owner.setCreate_date(_creationDate);
                owner.setLast_login(_lastLoginDate);
                owner.setHome(home);
                long id = dbAdapter.insertINOwners(owner);
                if(id > 0) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}