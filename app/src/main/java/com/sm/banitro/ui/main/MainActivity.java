package com.sm.banitro.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Demand;
import com.sm.banitro.ui.incoming.IncomingFragment;
import com.sm.banitro.ui.productdetail.ProductDetailFragment;
import com.sm.banitro.ui.profile.ProfileFragment;
import com.sm.banitro.ui.recent.RecentFragment;

public class MainActivity extends AppCompatActivity implements RecentFragment.Interaction {

    // ********************************************************************************
    // Instance
    private FragmentManager fragmentManager;
    private ProfileFragment profileFragment;
    private RecentFragment recentFragment;
    private IncomingFragment incomingFragment;
    private ProductDetailFragment productDetailFragment;
    // View
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private Toast toast;

    // ********************************************************************************
    // Basic Override

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.activity_main_bottom_navigation);
        toolbar = findViewById(R.id.activity_main_toolbar);
        fragmentManager = getSupportFragmentManager();
        profileFragment = (ProfileFragment) fragmentManager
                .findFragmentByTag(ProfileFragment.class.getName());
        recentFragment = (RecentFragment) fragmentManager
                .findFragmentByTag(RecentFragment.class.getName());
        incomingFragment = (IncomingFragment) fragmentManager
                .findFragmentByTag(IncomingFragment.class.getName());
        productDetailFragment = (ProductDetailFragment) fragmentManager
                .findFragmentByTag(ProductDetailFragment.class.getName());
        toast = Toast.makeText(this, R.string.toast_click_again_to_exit, Toast.LENGTH_SHORT);
        setValueBottomNavigation();
    }

    // ********************************************************************************
    // Initialization

    private void setValueBottomNavigation() {

        bottomNavigation.setSelectedItemId(R.id.bottom_navigation_recent);
        toolbar.setTitle(R.string.recent);
        goToRecentFragment();

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_navigation_profile:
                                toolbar.setTitle(R.string.profile);
                                goToProfileFragment();
                                break;
                            case R.id.bottom_navigation_recent:
                                toolbar.setTitle(R.string.recent);
                                goToRecentFragment();
                                break;
                            case R.id.bottom_navigation_incoming:
                                toolbar.setTitle(R.string.incoming);
                                goToIncomingFragment();
                                break;
                        }
                        return true;
                    }
                });
    }

    // ********************************************************************************
    // Method

    private void goToProfileFragment() {
        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();
            if (incomingFragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.activity_main_frame_layout, profileFragment, ProfileFragment.class.getName())
                        .hide(recentFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.activity_main_frame_layout, profileFragment, ProfileFragment.class.getName())
                        .hide(recentFragment)
                        .hide(incomingFragment)
                        .commit();
            }
        } else {
            if (recentFragment == null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(incomingFragment).show(profileFragment).commit();
            } else if (recentFragment != null && incomingFragment == null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).show(profileFragment).commit();
            } else if (recentFragment != null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).hide(incomingFragment).show(profileFragment).commit();
            }
        }
    }

    private void goToRecentFragment() {
        if (recentFragment == null) {
            recentFragment = recentFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_main_frame_layout, recentFragment, RecentFragment.class.getName())
                    .commit();
        } else {
            if (profileFragment == null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(incomingFragment).show(recentFragment).commit();
            } else if (profileFragment != null && incomingFragment == null) {
                fragmentManager.beginTransaction()
                        .hide(profileFragment).show(recentFragment).commit();
            } else if (profileFragment != null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(profileFragment).hide(incomingFragment).show(recentFragment).commit();
            }
        }
    }

    private void goToIncomingFragment() {
        if (incomingFragment == null) {
            incomingFragment = IncomingFragment.newInstance();
            if (profileFragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.activity_main_frame_layout, incomingFragment, IncomingFragment.class.getName())
                        .hide(recentFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.activity_main_frame_layout, incomingFragment, IncomingFragment.class.getName())
                        .hide(recentFragment)
                        .hide(profileFragment)
                        .commit();
            }
        } else {
            if (recentFragment == null && profileFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(profileFragment).show(incomingFragment).commit();
            } else if (recentFragment != null && profileFragment == null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).show(incomingFragment).commit();
            } else if (recentFragment != null && profileFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).hide(profileFragment).show(incomingFragment).commit();
            }
        }
    }

    // ********************************************************************************
    // Implement

    @Override
    public void goToProductDetailFragment(Demand demand) {
        if (productDetailFragment == null) {
                productDetailFragment = ProductDetailFragment.newInstance(demand);
            fragmentManager.beginTransaction()
            .add(R.id.activity_main_frame_layout, productDetailFragment, ProductDetailFragment.class.getName())
            .addToBackStack(ProductDetailFragment.class.getName())
            .commit();
        }
    }

    // ********************************************************************************
    // Supplementary Override

    @Override
    public void onBackPressed() {
        if (toast.getView().isShown()) {
            toast.cancel();
            super.onBackPressed();
            return;
        } else {
            toast.show();
        }
    }
}