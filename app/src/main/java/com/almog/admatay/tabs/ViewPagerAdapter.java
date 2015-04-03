package com.almog.admatay.tabs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.almog.admatay.fragments.DataFragment;
import com.almog.admatay.fragments.DayListFragment;
import com.almog.admatay.fragments.NightListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int mNumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public ViewPagerAdapter(FragmentManager fm, String[] mTitles, int mNumbOfTabs) {
        super(fm);

        this.mTitles = mTitles;
        this.mNumbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            DataFragment tab1 = new DataFragment();
            return tab1;
        }
        else if (position == 1){
            DayListFragment tab2 = new DayListFragment();
            return tab2;
        }
        else {
            NightListFragment tab3 = new NightListFragment();
            return tab3;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mNumbOfTabs;
    }
}
