package com.bsoft.libcommon.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainTabAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;

    public MainTabAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
    }
    public void setData(List<Fragment> data){
        if (fragments == null){
            fragments = new ArrayList<>();
        }
        fragments.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

