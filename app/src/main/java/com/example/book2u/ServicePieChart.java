package com.example.book2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicePieChart extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String sid;
    FirebaseAuth mFirebaseAuth;
    ArrayList<BookCarWash> list;
    int service,countdata;
    int c1=0,c2=0,c3=0,c4=0,c5=0, c6=0,c7=0;
    float s1,s2,s3,s4,s5,s6,s7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_pie_chart);

        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> s = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("BookCarWash");
        sid = user.getUid();
        String[] ser=new String[] {"Car Wash","Car Polish","Car Coating","Tyre Shining","Polish","Engine Cleaning"};
        int count[]=new int[7];
        //List<String> lservice= new ArrayList<String>(Arrays.asList(ls));
        //ls=lservice.toArray(ls);
        ArrayList<String> als= new ArrayList<>();
        list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    BookCarWash userProfile = datasnapshot.getValue(BookCarWash.class);
                    //Log.e("book", "onDataChange: "+userProfile.services );
                    list.add(userProfile);
                    als.add(userProfile.services);
                }
                for(int i =0;i<als.size();i++){
                    if (als.get(i).toString().equals("Car Wash")){
                        c1++;
                    } else if (als.get(i).equals("Car Grooming")) {
                        c2++;
                    }
                    else if (als.get(i).equals("Car Polish")){
                        c3++;
                    } else if (als.get(i).equals("Car Coating")) {
                        c4++;
                    }else if (als.get(i).equals("Tyre Shining")){
                        c5++;
                    }else if (als.get(i).equals("Polish")){
                        c6++;
                    }else if (als.get(i).equals("Engine Cleaning")){
                        c7++;
                    }
                }
                s1=(float) c1;
                s2=(float) c2;
                s3=(float) c3;
                s4=(float) c4;
                s5=(float) c5;
                s6=(float) c6;
                s7=(float) c7;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        s.add(new PieEntry(11,"Car Wash"));
        s.add(new PieEntry(7, "Car Grooming"));
        s.add(new PieEntry(12,"Car Polish"));
        s.add(new PieEntry(11, "Car Coating"));
        s.add(new PieEntry(10,"Tyre Shining"));
        s.add(new PieEntry(5, "Polish"));
        s.add(new PieEntry(7,"Engine Cleaning"));



        PieDataSet pieDataSet = new PieDataSet(s, "Service");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText("Service");
        pieChart.animate();
    }
}