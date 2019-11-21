package com.example.homework07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.homework07.dummy.DummyContent;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements UsersFragment.OnListFragmentInteractionListener, TripsFragment.OnListFragmentInteractionListener{

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Intent i;

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_24px);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_airport);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_group);

        //i = new Intent(MainActivity.this, ChatActivity.class);
        //startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(Trip item) {

        i = new Intent(MainActivity.this, TripActivity.class);
        startActivity(i);

    }
}
