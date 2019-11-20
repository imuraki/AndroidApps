package com.example.homework07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.homework07.dummy.DummyContent;
import com.google.android.material.tabs.TabLayout;

public class TripActivity extends AppCompatActivity implements TripsFragment.OnListFragmentInteractionListener, UsersFragment.OnListFragmentInteractionListener{

    ViewPager trippager;
    TripPagerAdapter tripPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);


        trippager = findViewById(R.id.trippager);
        tripPagerAdapter = new TripPagerAdapter(getSupportFragmentManager());
        trippager.setAdapter(tripPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(trippager);

    }

    @Override
    public void onListFragmentInteraction(Trip item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
