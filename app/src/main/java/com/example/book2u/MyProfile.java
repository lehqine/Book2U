package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MyProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String uid;
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }
        else{
            setTheme(R.style.Theme_Light);
        }

        drawerlayout = findViewById(R.id.navbar_staff);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView user = (TextView) findViewById(R.id.FullName);
        final TextView emaila = (TextView) findViewById(R.id.EmailAddress);
        final TextView phone = (TextView) findViewById(R.id.Phone);
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@org.checkerframework.checker.nullness.qual.NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String fullname = userProfile.username;
                    String emailaddr = userProfile.email;
                    String phonenum = userProfile.PhoneNum;

                    user.setText(fullname);
                    emaila.setText(emailaddr);
                    phone.setText(phonenum);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfile.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void ClickMenu(View view){
        CustHomePage.openDrawer(drawerlayout);
    }

    public void ClickFAQ(View view) {CustHomePage.redirectActivity(this, fragmentMain.class);   }

    public void ClickHome(View view){
        CustHomePage.redirectActivity(this,CustHomePage.class);
    }
    public void ClickSetting(View view) {CustHomePage.redirectActivity(this,Setting.class);   }
    public void ClickMyProfile(View view){
        CustHomePage.closeDrawer(drawerlayout);
    }


    public void ClickLogOut(View view){
        CustHomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        CustHomePage.closeDrawer(drawerlayout);
    }
    //<<<<<<<NAVBAR CODE SECTION ENDS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}