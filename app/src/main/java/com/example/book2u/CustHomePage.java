package com.example.book2u;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.book2u.adapter.PackageRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustHomePage extends AppCompatActivity implements View.OnClickListener{


    DrawerLayout drawerLayout;
    LinearLayoutManager linearLayoutManager;
    CardView booking, aboutus, rating, history;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home_page);

        drawerLayout = findViewById(R.id.navbar_staff);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        booking = (CardView) findViewById(R.id.card_booking);
        aboutus = (CardView) findViewById(R.id.card_aboutus);
        rating = (CardView) findViewById(R.id.card_rating);
        history = (CardView) findViewById(R.id.card_history);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView username = (TextView) findViewById(R.id.txt_user_name);
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String fullname = userProfile.username;
                    username.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustHomePage.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }
        else{
            setTheme(R.style.Theme_Light);
        }

        linearLayoutManager = new LinearLayoutManager(CustHomePage.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Package> allPackageInfor = getAllPackageInfor();
        PackageRecyclerViewAdapter packageRecyclerViewAdapter = new PackageRecyclerViewAdapter(CustHomePage.this,allPackageInfor);
        recyclerView.setAdapter(packageRecyclerViewAdapter);

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        booking.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        rating.setOnClickListener(this);
        history.setOnClickListener(this);

    }

    private List<Package> getAllPackageInfor(){
        List<Package> allPackage = new ArrayList<Package>();

        allPackage.add(new Package("Interior Car Detailing",R.drawable.interiorcardetailing));
        allPackage.add(new Package("Car Seat Detailing",R.drawable.carseatdetailing));
        allPackage.add(new Package("Exterior Car Detailing",R.drawable.exteriorcardetailing));
        allPackage.add(new Package("Polishing",R.drawable.exteriorcardetailing2));
        allPackage.add(new Package("Full Car Detailing",R.drawable.fullcardetailing));
        allPackage.add(new Package("Car Seat Detailing",R.drawable.fullcardetailing2));

        return allPackage;

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.card_booking:
                startActivity(new Intent(this,CustBooking.class));
                break;

            case R.id.card_history:
                startActivity(new Intent(this,CustBookHistory.class));
                break;

            case R.id.card_rating:
                startActivity(new Intent(this,Rating.class));
                break;
            case R.id.card_aboutus:
                startActivity(new Intent(this,AboutUsActivity.class));
        }

    }

    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerlayout) {
        drawerlayout.openDrawer(GravityCompat.START);
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickSetting(View view) {redirectActivity(this, Setting.class);   }
    public void ClickMyProfile(View view) {redirectActivity(this, MyProfile.class);   }

    public void ClickFAQ(View view) {redirectActivity(this, fragmentMain.class);   }

       public void ClickLogOut(View view) {
        logout(this);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }
    public static void redirectActivity(Activity activity, Class nClass) {
        Intent intent = new Intent(activity, nClass);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();

                redirectActivity(activity,MainActivity.class);

                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    //<<<<<<<NAVBAR CODE SECTION ENDS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}