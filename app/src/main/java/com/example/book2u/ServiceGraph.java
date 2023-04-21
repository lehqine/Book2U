package com.example.book2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceGraph extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String sid;
    FirebaseAuth mFirebaseAuth;
    ArrayList<BookCarWash> list;
    int service,countdata,c1=0,c2=0,c3=0,c4=0,c5=0, c6=0,c7=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_graph);

        BarChart barChart = findViewById(R.id.barChart);
        list = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("BookCarWash");
        sid = user.getUid();
        ArrayList<BarEntry> s= new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    String rating = dataSnapshot.getKey();

                    if (rating.equals("Car Wash")){
                        c1++;
                    } else if (rating.equals("Car Grooming")) {
                        c2++;
                    }
                    else if (rating.equals("Car Polish")){
                        c3++;
                    } else if (rating.equals("Car Coating")) {
                        c4++;
                    }else if (rating.equals("Tyre Shining")){
                        c5++;
                    }else if (rating.equals("Polish")){
                        c6++;
                    }else if (rating.equals("Engine Cleaning")){
                        c7++;
                    }
                    //countrate = (int) datasnapshot.getChildrenCount();
                    //c=c+countrate;

                }
                s.add(new BarEntry(1,c1));
                s.add(new BarEntry(2,c2));
                s.add(new BarEntry(3,c3));
                s.add(new BarEntry(4,c4));
                s.add(new BarEntry(5,c5));
                s.add(new BarEntry(6,c6));
                s.add(new BarEntry(7,c7));
                countdata = c1+c2+c3+c4+c5+c6+c7;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BarDataSet barDataSet = new BarDataSet(s,"Service");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Service");
        //barChart.animateY(2000);
    }
}