package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bgapp;
    LinearLayout splashtext, hometext, linearLayout_menu1, linearLayout_menu2;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.imageView_bg);
        splashtext = (LinearLayout) findViewById(R.id.splashtext);
        hometext = (LinearLayout) findViewById(R.id.hometext);

        linearLayout_menu1 = (LinearLayout) findViewById(R.id.linearLayout_menu1);
        linearLayout_menu2 = (LinearLayout) findViewById(R.id.linearLayout_menu2);

        bgapp.animate().translationY(-900).setDuration(800).setStartDelay(300);
        splashtext.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        hometext.startAnimation(frombottom);
        linearLayout_menu1.startAnimation(frombottom);
        linearLayout_menu2.startAnimation(frombottom);

        linearLayout_menu1.setOnClickListener(this);
        linearLayout_menu2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linearLayout_menu1:
                startActivity(new Intent(this,StaffLogin.class));
                break;

            case R.id.linearLayout_menu2:
                startActivity(new Intent(this,CustLogin.class));
                break;
        }

    }
}