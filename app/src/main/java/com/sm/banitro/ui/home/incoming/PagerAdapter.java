package com.sm.banitro.ui.home.incoming;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sm.banitro.ui.home.incoming.approved.ApprovedFragment;
import com.sm.banitro.ui.home.incoming.approvednot.ApprovedNotFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    // ********************************************************************************
    // Field

    // Instance
    private ApprovedFragment approvedFragment;
    private ApprovedNotFragment approvedNotFragment;

    // Data Type
    private final int TAB_NUM = 2;

    // ********************************************************************************
    // Constructor

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        approvedFragment = (ApprovedFragment) fragmentManager
                .findFragmentByTag(ApprovedFragment.class.getName());
        approvedNotFragment = (ApprovedNotFragment) fragmentManager
                .findFragmentByTag(ApprovedNotFragment.class.getName());
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (approvedFragment == null) {
                approvedFragment = approvedFragment.newInstance();
            }
            return approvedFragment;
        } else if (position == 1) {
            if (approvedNotFragment == null) {
                approvedNotFragment = approvedNotFragment.newInstance();
            }
            return approvedNotFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_NUM;
    }
}