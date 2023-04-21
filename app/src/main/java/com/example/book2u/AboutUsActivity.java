package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imgBtnCall, imgBtnEmail, imgBtnStore, imgBtnLocation ;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout = findViewById(R.id.navbar_staff);
        imgBtnCall = findViewById(R.id.img_btn_call_order_act);
        imgBtnEmail = findViewById(R.id.img_btn_email_order_act);
        imgBtnStore = findViewById(R.id.img_btn_store_order_act);
        imgBtnLocation = findViewById(R.id.img_btn_location_order_act);

        imgBtnCall.setOnClickListener(this);
        imgBtnEmail.setOnClickListener(this);
        imgBtnStore.setOnClickListener(this);
        imgBtnLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_btn_call_order_act:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: 0123456789"));

                if(callIntent.resolveActivity(getPackageManager()) != null){ //verify that an app exists to receive the intent
                    startActivity(callIntent);
                }
                else{
                    Toast.makeText(AboutUsActivity.this,"Sorry, no app can handle this action and data",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.img_btn_email_order_act:

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Enquiries on Book2U"); //email title
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Email message: Information about car wash booking."); //message want to send
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"techninjasq4u@gmail.com"});

                if(emailIntent.resolveActivity(getPackageManager()) != null){ //verify that an app exists to receive the intent
                    startActivity(emailIntent);
                }
                else{
                    Toast.makeText(AboutUsActivity.this,"Sorry, no app can handle this action and data",Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.img_btn_store_order_act:

                Uri storepage = Uri.parse("https://www.facebook.com/people/Empire-Car-Wash-Bangi/100064229075914/");
                Intent storeIntent = new Intent(Intent.ACTION_VIEW,storepage);

                if(storeIntent.resolveActivity(getPackageManager()) != null){ //verify that an app exists to receive the intent
                    startActivity(storeIntent);
                }
                else{
                    Toast.makeText(AboutUsActivity.this,"Sorry, no app can handle this action and data",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.img_btn_location_order_act:

                Uri mappage = Uri.parse("https://www.google.com.my/maps/dir/2.9246692,101.7862428/empire+car+wash+bangi/@2.9439691,101.7637815,14z/data=!3m1!4b1!4m9!4m8!1m1!4e1!1m5!1m1!1s0x31cdcb62d21c2abb:0x40404be15f1172a7!2m2!1d101.7788822!2d2.9647556");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,mappage);

                if(mapIntent.resolveActivity(getPackageManager()) != null){ //verify that an app exists to receive the intent
                    startActivity(mapIntent);
                }
                else{
                    Toast.makeText(AboutUsActivity.this,"Sorry, no app can handle this action and data",Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void ClickMenu(View view){
        CustHomePage.openDrawer(drawerLayout);
    }


    public void ClickHome(View view){
        CustHomePage.redirectActivity(this,CustHomePage.class);
    }

    public void ClickFAQ(View view) {CustHomePage.redirectActivity(this, fragmentMain.class);   }
    public void ClickSetting(View view) {CustHomePage.redirectActivity(this, Setting.class);   }

    public void ClickMyProfile(View view){
        CustHomePage.redirectActivity(this,MyProfile.class);
    }


    public void ClickLogOut(View view){
        CustHomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        CustHomePage.closeDrawer(drawerLayout);
    }
    //<<<<<<<NAVBAR CODE SECTION ENDS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}