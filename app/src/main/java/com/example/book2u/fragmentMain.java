package com.example.book2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.book2u.adapter.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class fragmentMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmain);

        tabLayout = findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter=new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment1(),"FAQ 1");
        vpAdapter.addFragment(new fragment2(),"FAQ 2");
        vpAdapter.addFragment(new fragment3(),"FAQ 3");
        viewPager.setAdapter(vpAdapter);

    }
}
