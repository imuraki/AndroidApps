package com.example.homework07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TripPagerAdapter extends FragmentStatePagerAdapter {

    public TripPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        if(i == 0)
            fragment = new TripsFragment();
        else if(i == 1)
            fragment = new UsersFragment();
        else
            fragment = new TripsFragment();

        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(TripsFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "USERS";
        else if(position == 1)
            return "PLACES";
        else
            return "MAP";

    }
}
