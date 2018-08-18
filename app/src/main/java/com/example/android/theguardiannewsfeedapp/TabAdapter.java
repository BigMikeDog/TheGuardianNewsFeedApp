package com.example.android.theguardiannewsfeedapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    TabAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment currentFragment=null;
        switch (position){
            case 0:{
                currentFragment=new NuclearEnergyFragment();
                break;
            }
            case 1:{
                currentFragment=new SportsFragment();
                break;
            }
            case 2:{
                currentFragment=new GamesFragment();
                break;
            }
        }
        return currentFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle=null;
        switch (position){
            case 0:{
                pageTitle=mContext.getString(R.string.category_nuclear_energy);
                break;
            }
            case 1:{
                pageTitle=mContext.getString(R.string.category_sports);
                break;
            }
            case 2:{
                pageTitle=mContext.getString(R.string.category_games);
                break;
            }
        }
        return pageTitle;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
