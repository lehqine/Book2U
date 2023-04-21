package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PackageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        Intent intent = getIntent();

        TextView tvName = findViewById(R.id.tv_package_name_detail);
        //ImageView imgName = findViewById(R.id.img_package_detail);

        tvName.setText(intent.getStringExtra("packageName"));
        //imgName.setImageResource(intent.get);
    }
}