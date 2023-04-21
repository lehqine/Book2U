package com.example.book2u;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffHomepage extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    BookHistoryAdapter bookHistoryAdapter;
    ArrayList<BookCarWash> list;
    Button logout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private DatabaseReference countref;
    private Dialog dialog;
    private String sid;
    private String rid;
    //addon
    Button count;
    private String curentUserId;
    int countrate=0;
    int countdata;
    int c=0;
    int rating,c1=0,c2=0,c3=0,c4=0,c5=0, c6=0,c7=0;
    Rate rate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_homepage);
        mFirebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rv_custhistory);
        logout = findViewById(R.id.button_staffLogout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        bookHistoryAdapter = new BookHistoryAdapter(this, list);
        recyclerView.setAdapter(bookHistoryAdapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        sid = user.getUid();
        database = FirebaseDatabase.getInstance().getReference("BookCarWash");

        //addon
        count = findViewById(R.id.btn_graph);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffHomepage.this,ServicePieChart.class));
            }
        });
        // curentUserId = mFirebaseAuth.getCurrentUser().getUid();
        /*countref = FirebaseDatabase.getInstance().getReference("BookCarWash");
        countref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    BookCarWash userProfile = datasnapshot.getValue(BookCarWash.class);
                    Log.e("book", "onDataChange: "+userProfile.services );
                    list.add(userProfile);
                    String se = userProfile.services;

                    if (se.equals("Car Wash")){
                        c1++;
                    } else if (se.equals("Car Grooming")) {
                        c2++;
                    }
                    else if (se.equals("Car Polish")){
                        c3++;
                    } else if (se.equals("Car Coating")) {
                        c4++;
                    }else if (se.equals("Tyre Shining")){
                        c5++;
                    }else if (se.equals("Polish")){
                        c6++;
                    }else if (se.equals("Engine Cleaning")){
                        c7++;
                    }
                    //countrate = (int) datasnapshot.getChildrenCount();
                    //c=c+countrate;

                }
                countdata = c1+c2+c3+c4+c5+c6+c7;
                count.setText(Integer.toString(countdata)+ "   Service");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        final TextView username = (TextView) findViewById(R.id.txt_user_name);
        databaseReference.child(sid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Toast.makeText(StaffHomepage.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BookCarWash bookCarWash = dataSnapshot.getValue(BookCarWash.class);
                    list.add(bookCarWash);
                }
                bookHistoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void ClickLogOut(View view) {
        logout(this);
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

                redirectActivity(activity, MainActivity.class);

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

    }}