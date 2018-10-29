package com.hoge.hogeapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

//    // insert dummy data
//    private CharSequence[] tabTitles = {"tab1", "tab2", "tab3", "tab4"};

    public MainFragmentPagerAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FugaFragment();
            case 1:
                return new HogeFragment();
            default:
                return new PiyoFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) { return null; }

    @Override
    public int getCount() { return MainActivity.tabLength; }

}
