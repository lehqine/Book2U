package com.example.book2u;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustServiceRating extends Dialog {

    private float useRate = 0 ;
    FirebaseUser user;
    String uid;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    DatabaseReference bookingDBRef;


    public CustServiceRating(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cust_service_rating);

        final AppCompatButton rateNowbtn = findViewById(R.id.rateNow_btn);
        final AppCompatButton laterbtn = findViewById(R.id.later_btn);
        final RatingBar ratingBar = findViewById(R.id.ratingbar);
        final ImageView ratingView = findViewById(R.id.imageview);
        float rating = ratingBar.getRating();
        useRate = rating;

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Rating");
        uid = user.getUid();

        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Rate userate = snapshot.getValue(Rate.class);

                if(userate!=null){
                    String fullname = userate.username;
                    userate.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookingDBRef = FirebaseDatabase.getInstance().getReference().child("rate").child(uid);
        rateNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code here
                //save data in database
                float rating = ratingBar.getRating();
                //mRatingBarCh.child("rating").setValue(String.valueOf(rating));
                insertRateData();
                Intent intent = new Intent(view.getContext(), CustHomePage.class);
                view.getContext().startActivity(intent);
                //Toast.makeText(rating, "Data Inserted", Toast.LENGTH_SHORT).show();

            }
        });

        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CustHomePage.class);
                view.getContext().startActivity(intent);

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                if(v <= 1) {
                    ratingView.setImageResource(R.drawable.one_star);
                }
                else if(v<=2){
                    ratingView.setImageResource(R.drawable.two_star);
                }
                else if(v<=3){
                    ratingView.setImageResource(R.drawable.three_star);
                }
                else if(v<=4){
                    ratingView.setImageResource(R.drawable.four_star);
                }
                else if(v<=5){
                    ratingView.setImageResource(R.drawable.five_star);
                }

                animateImage(ratingView);
                useRate = v;
            }
        });
    }

    private void insertRateData() {
       //Float rate = useRate;
       Rate uid = new Rate(useRate);
       bookingDBRef.push().setValue(uid);

    }


    private void setServiceRate(float userate) {
        this.useRate=userate;
    }

    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1f,0,01f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }


}
