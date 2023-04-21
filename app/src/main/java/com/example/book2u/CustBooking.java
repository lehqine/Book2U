package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Calendar;

public class CustBooking extends AppCompatActivity{

    EditText etNickname, etCarPlateNum, etCarModel;
    Button btnSubmit, btndate, btntime;
    Spinner spinner_services, spinner_carType, spinner_timeSlot;
    TimePicker timePicker;
    DrawerLayout drawerLayout;
    FirebaseUser user;
    String uid;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    DatabaseReference bookingDBRef, bookingDBRef1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_booking);

        drawerLayout = findViewById(R.id.navbar_staff);
        etNickname = findViewById(R.id.et_nickname);
        etCarPlateNum = findViewById(R.id.et_carNum);
        etCarModel = findViewById(R.id.et_CarModel);
        btnSubmit = findViewById(R.id.btn_submit);
        spinner_services = findViewById(R.id.TypeServices);
        spinner_carType = findViewById(R.id.CarType);
        spinner_timeSlot = findViewById(R.id.timeSlot);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView username = (TextView) findViewById(R.id.et_nickname);
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
                Toast.makeText(CustBooking.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        bookingDBRef = FirebaseDatabase.getInstance().getReference().child("Booking").child(uid);
        bookingDBRef1=FirebaseDatabase.getInstance().getReference("BookCarWash");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBookData();
            }
        });
    }

    private void insertBookData(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView username = (TextView) findViewById(R.id.et_nickname);
        //final TextView emaila = (TextView) findViewById(R.id.EmailAddress);
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String fullname = userProfile.username;
                    //String emailaddr=userProfile.email;
                    username.setText(fullname);
                    //emaila.setText(emailaddr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustBooking.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        String nickname = etNickname.getText().toString();
        String plateNum = etCarPlateNum.getText().toString();
        String carModel = etCarModel.getText().toString();
        String services = spinner_services.getSelectedItem().toString();
        String carType = spinner_carType.getSelectedItem().toString();
        String timeSlot = spinner_timeSlot.getSelectedItem().toString();

        if (!etNickname.getText().toString().isEmpty() && !etCarPlateNum.getText().toString().isEmpty() && !etCarModel
                .getText().toString().isEmpty()) {
            BookCarWash uid = new BookCarWash(nickname,plateNum,carModel,services,carType,timeSlot);
            bookingDBRef.push().setValue(uid);
            bookingDBRef1.push().setValue(uid);
            Toast.makeText(CustBooking.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);

            intent.putExtra(CalendarContract.Events.TITLE, spinner_services.getSelectedItem().toString());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Reminder On Your Car Wash Service Booking");
            intent.putExtra(CalendarContract.Events.ALL_DAY, false);

            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }else{
                Toast.makeText(CustBooking.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(CustBooking.this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //>>>>>>>NAVBAR CODE SECTION STARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void ClickMenu(View view){
        CustHomePage.openDrawer(drawerLayout);
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