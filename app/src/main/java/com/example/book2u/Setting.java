package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

public class Setting extends AppCompatActivity {
    DrawerLayout drawerlayout;
    LinearLayout ll_setting_darkmode;
    Switch switch_dark;
    SharedPreferences sharePref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }
        else{
            setTheme(R.style.Theme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        drawerlayout = findViewById(R.id.dl_setting);
        ll_setting_darkmode = findViewById(R.id.ll_profile);
        switch_dark = findViewById(R.id.darkmode_switch);

        sharePref = getSharedPreferences("app_settings", MODE_PRIVATE);
        editor = sharePref.edit();

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            switch_dark.setChecked(true);
        }

        switch_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }
    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public void ClickMenu(View view) {
        openDrawer(drawerlayout);
    }
    public static void openDrawer(DrawerLayout drawerlayout) {
        drawerlayout.openDrawer(GravityCompat.START);
    }
    public void ClickFAQ(View view) {CustHomePage.redirectActivity(this, fragmentMain.class);   }
    public void ClickHome(View view) {
        CustHomePage.redirectActivity(this,CustHomePage.class);
    }
    public void ClickSetting(View view) {CustHomePage.closeDrawer(drawerlayout);}
    public void ClickMyProfile(View view){
        CustHomePage.redirectActivity(this,MyProfile.class);
    }
    public void ClickLogOut(View view) {
        CustHomePage.logout(this);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerlayout);
    }
    //<<<<<<<NAVBAR CODE SECTION ENDS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}