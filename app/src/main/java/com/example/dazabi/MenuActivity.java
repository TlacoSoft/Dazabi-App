package com.example.dazabi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void qr(View v){
        MenuActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MenuActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void ventas(View v){
        url = "https://salmon-glacier-0828f0d10.1.azurestaticapps.net";
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }
}