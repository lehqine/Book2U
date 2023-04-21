package com.example.book2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;

public class CustBookHistory extends AppCompatActivity {

    //RecyclerView recyclerView;
    ListView li;
    DatabaseReference database;
    RecyclerView recyclerView;
    BookHistoryAdapter bookHistoryAdapter;
    ArrayList<BookCarWash> list;
    DrawerLayout drawerLayout;
    FirebaseUser user;
    String uid;
    List<String> itemlist;
    private DatabaseReference databaseReference;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cust_book_history);

        drawerLayout = findViewById(R.id.navbar_staff);
        recyclerView = findViewById(R.id.recyclerBookHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        bookHistoryAdapter = new BookHistoryAdapter(this, list);
        recyclerView.setAdapter(bookHistoryAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Booking").child(uid);

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

        //final TextView carplate = (TextView) findViewById(R.id.tvTitle);
        /*databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datasnapshot : snapshot.getChildren()){

                    String carPlate = datasnapshot.child("carPlate").getValue(String.class);
                    String carModel= datasnapshot.child("carModel").getValue(String.class);
                    String services= datasnapshot.child("services").getValue(String.class);
                    String carType= datasnapshot.child("carType").getValue(String.class);
                    String timeSlot= datasnapshot.child("timeSlot").getValue(String.class);
                    itemlist.add(carPlate);
                    itemlist.add(carModel);
                    itemlist.add(services);
                    itemlist.add(carType);
                    itemlist.add(timeSlot);
                }
                adapter = new ArrayAdapter<>(CustBookHistory.this, android.R.layout.simple_list_item_1, itemlist);
                li.setAdapter(adapter);
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CustBookHistory.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            });*/

        /*database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BookCarWash bookCarWash = dataSnapshot.getValue(BookCarWash.class);
                    list.add(bookCarWash);
                }
                bookHistoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/


    }
    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void ClickMenu(View view){CustHomePage.openDrawer(drawerLayout);
    }

    public void ClickFAQ(View view) {CustHomePage.redirectActivity(this, fragmentMain.class);   }
    public void ClickHome(View view){
        CustHomePage.redirectActivity(this,CustHomePage.class);
    }
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