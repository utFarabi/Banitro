package com.sm.banitro.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.ui.home.incoming.IncomingFragment;
import com.sm.banitro.ui.home.profile.ProfileFragment;
import com.sm.banitro.ui.home.recent.RecentFragment;
import com.sm.banitro.ui.productdetail.ProductDetailFragment;
import com.sm.banitro.ui.productdetail.ReplyDialogFragment;
import com.sm.banitro.util.Function;

public class MainActivity extends AppCompatActivity
        implements RecentFragment.Interaction, ProductDetailFragment.Interaction {

    // ********************************************************************************
    // Instance
    private FragmentManager fragmentManager;
    private ProfileFragment profileFragment;
    private RecentFragment recentFragment;
    private IncomingFragment incomingFragment;
    private ProductDetailFragment productDetailFragment;
    private DialogFragment dialogFragmentNetwork;
    private InternalNetworkChangeReceiver internalNetworkChangeReceiver;
    // Type
    private String fragmentName;
    private boolean networkDialogIsShowing;
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

        internalNetworkChangeReceiver = new InternalNetworkChangeReceiver();

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

        registerReceiver();
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

    private void registerReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);
            registerReceiver(internalNetworkChangeReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ********************************************************************************
    // Method

    private void goToProfileFragment() {
        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();
            if (incomingFragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
                        .hide(recentFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
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
            if (Function.isNetworkAvailable(this)) {
                recentFragment = recentFragment.newInstance();
                fragmentManager.beginTransaction()
                        .add(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                        .commit();
            } else {
                fragmentName = "RecentFragment";
                goToInternetDialog();
            }
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
            if (Function.isNetworkAvailable(this)) {
                incomingFragment = IncomingFragment.newInstance();
                if (profileFragment == null) {
                    fragmentManager.beginTransaction()
                            .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
                            .hide(recentFragment)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
                            .hide(recentFragment)
                            .hide(profileFragment)
                            .commit();
                }
            } else {
                fragmentName = "IncomingFragment";
                goToInternetDialog();
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

    public void goToInternetDialog() {
        dialogFragmentNetwork = NetworkDialogFragment.newInstance();
        dialogFragmentNetwork.show(fragmentManager.beginTransaction(), NetworkDialogFragment.class.getName());
        dialogFragmentNetwork.setCancelable(false);
        networkDialogIsShowing = true;
    }

    // ********************************************************************************
    // Implement

    @Override
    public void goToProductDetailFragment(Product product) {
        productDetailFragment = ProductDetailFragment.newInstance(product);
        fragmentManager.beginTransaction()
                .add(R.id.base_layout, productDetailFragment, ProductDetailFragment.class.getName())
                .hide(recentFragment)
                .addToBackStack(ProductDetailFragment.class.getName())
                .commit();

    }

    @Override
    public void goToReplyDialogFragment(Product product) {
        DialogFragment dialogFragment = ReplyDialogFragment.newInstance(product);
        dialogFragment.show(fragmentManager.beginTransaction(), ReplyDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

    // ********************************************************************************
    // Supplementary Override

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            if (toast.getView().isShown()) {
                toast.cancel();
                super.onBackPressed();
                return;
            } else {
                toast.show();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(internalNetworkChangeReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    class InternalNetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("status", false) && networkDialogIsShowing) {
                dialogFragmentNetwork.dismiss();
                networkDialogIsShowing = false;
                switch (fragmentName) {
                    case "RecentFragment":
                        goToRecentFragment();
                        break;
                    case "IncomingFragment":
                        goToIncomingFragment();
                        break;
                }
            }
        }
    }
}