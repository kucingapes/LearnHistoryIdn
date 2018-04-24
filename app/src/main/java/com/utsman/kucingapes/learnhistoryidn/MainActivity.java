package com.utsman.kucingapes.learnhistoryidn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void menu(View view) {
        startActivity(new Intent(MainActivity.this, MainMenu.class));
    }

    public void detel(View view) {
        startActivity(new Intent(this, Input.class));
    }
}
