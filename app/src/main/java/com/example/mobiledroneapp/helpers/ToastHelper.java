package com.example.mobiledroneapp.helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
