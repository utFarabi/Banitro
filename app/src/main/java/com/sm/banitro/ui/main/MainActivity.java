// In the name of Allah
package com.sm.banitro.ui.main;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.local.AppPreferences;
import com.sm.banitro.ui.editdialog.EditCategoryDialogFragment;
import com.sm.banitro.ui.editdialog.EditTextDialogFragment;
import com.sm.banitro.ui.firstpage.FirstPageFragment;
import com.sm.banitro.ui.firstpage.LoginDialogFragment;
import com.sm.banitro.ui.firstpage.register.RegisterFragment;
import com.sm.banitro.ui.home.incoming.IncomingFragment;
import com.sm.banitro.ui.home.incoming.approved.ApprovedFragment;
import com.sm.banitro.ui.home.incoming.approvednot.ApprovedNotFragment;
import com.sm.banitro.ui.home.profile.ProfileFragment;
import com.sm.banitro.ui.home.recent.DeleteDialogFragment;
import com.sm.banitro.ui.home.recent.RecentFragment;
import com.sm.banitro.ui.incomingdetail.IncomingDetailFragment;
import com.sm.banitro.ui.main.logout.LogoutDialogFragment;
import com.sm.banitro.ui.main.network.NetworkDialogFragment;
import com.sm.banitro.ui.main.network.NetworkReceiver;
import com.sm.banitro.ui.main.notification.NotificationService;
import com.sm.banitro.ui.recentdetail.RecentDetailFragment;
import com.sm.banitro.ui.recentdetail.ReplyDialogFragment;
import com.sm.banitro.util.ConstantUtil;
import com.sm.banitro.util.FunctionUtil;
import com.sm.banitro.util.VersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements RecentFragment.Interaction, RecentDetailFragment.Interaction,
        NetworkReceiver.Interaction, ProfileFragment.Interaction,
        ApprovedFragment.Interaction, ApprovedNotFragment.Interaction,
        DeleteDialogFragment.Interaction, ReplyDialogFragment.Interaction,
        EditTextDialogFragment.Interaction, EditCategoryDialogFragment.Interaction,
        RegisterFragment.Interaction, FirstPageFragment.Interaction,
        LoginDialogFragment.Interaction, LogoutDialogFragment.Interaction {

    // ********************************************************************************
    // Field

    // Instance
    private AppPreferences pref;
    private FragmentManager fragmentManager;
    private RecentFragment recentFragment;
    private IncomingFragment incomingFragment;
    private ProfileFragment profileFragment;
    private RecentDetailFragment recentDetailFragment;
    private IncomingDetailFragment incomingDetailFragment;
    private DialogFragment networkDialogFragment, loginDialogFragment;
    private FirstPageFragment firstPageFragment;
    private RegisterFragment registerFragment;
    private NetworkReceiver networkReceiver;
    private Toast toast;
    private Unbinder unbinder;

    // Data Type
    private boolean networkDialogIsShowing;
    private final String NETWORK_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    // View
    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.activity_main_toolbar_logo)
    ImageView logo;
    @BindView(R.id.activity_main_toolbar_title)
    TextView title;

    // ********************************************************************************
    // Basic Override

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Instance
        unbinder = ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        pref = new AppPreferences(this);

        profileFragment = (ProfileFragment) fragmentManager
                .findFragmentByTag(ProfileFragment.class.getName());
        recentFragment = (RecentFragment) fragmentManager
                .findFragmentByTag(RecentFragment.class.getName());
        incomingFragment = (IncomingFragment) fragmentManager
                .findFragmentByTag(IncomingFragment.class.getName());
        recentDetailFragment = (RecentDetailFragment) fragmentManager
                .findFragmentByTag(RecentDetailFragment.class.getName());
        incomingDetailFragment = (IncomingDetailFragment) fragmentManager
                .findFragmentByTag(IncomingDetailFragment.class.getName());
        firstPageFragment = (FirstPageFragment) fragmentManager
                .findFragmentByTag(FirstPageFragment.class.getName());
        registerFragment = (RegisterFragment) fragmentManager
                .findFragmentByTag(RegisterFragment.class.getName());

        toast = Toast.makeText(this, R.string.toast_click_again_to_exit, Toast.LENGTH_SHORT);

        networkReceiver = new NetworkReceiver();
        NetworkReceiver.interaction = this;

        registerNetworkReceiver();

        if (pref.isFirstLogin()) {
            firstPageFragment = FirstPageFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.base_layout, firstPageFragment, FirstPageFragment.class.getName())
                    .commit();
        } else {
            setValueToBottomNavigation();
        }

        startNotificationService();
    }

    // ********************************************************************************
    // Initialization

    private void registerNetworkReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NETWORK_CHANGE_ACTION);
            registerReceiver(networkReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startNotificationService() {
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(ConstantUtil.ACTION_START_SERVICE);
        startService(intent);
    }

    private void setValueToBottomNavigation() {

        // First Item Selected
        bottomNavigation.setSelectedItemId(R.id.bottom_navigation_recent);
        title.setText(R.string.recent);
        logo.setImageResource(R.drawable.baseline_mail_white_24);
        if (FunctionUtil.isConnecting(this)) {
            goToRecentFragment();
        } else {
            goToNetworkDialogFragment();
        }

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_navigation_profile:
                                title.setText(R.string.profile);
                                logo.setImageResource(R.drawable.baseline_person_white_24);
                                if (!networkDialogIsShowing) {
                                    goToProfileFragment();
                                }
                                break;
                            case R.id.bottom_navigation_recent:
                                title.setText(R.string.recent);
                                logo.setImageResource(R.drawable.baseline_mail_white_24);
                                if (!networkDialogIsShowing) {
                                    goToRecentFragment();
                                }
                                break;
                            case R.id.bottom_navigation_incoming:
                                title.setText(R.string.incoming);
                                logo.setImageResource(R.drawable.baseline_drafts_white_24);
                                if (!networkDialogIsShowing) {
                                    goToIncomingFragment();
                                }
                                break;
                        }
                        return true;
                    }
                });
    }

    // ********************************************************************************
    // Method

    private void goToRecentFragment() {
        if (recentFragment == null) {
            recentFragment = RecentFragment.newInstance();
            if (registerFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                        .hide(firstPageFragment)
                        .hide(registerFragment)
                        .commit();
            } else if (firstPageFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                        .hide(firstPageFragment)
                        .commit();
            } else {
                if (profileFragment == null && incomingFragment != null) {
                    fragmentManager.beginTransaction()
                            .hide(incomingFragment)
                            .add(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                            .commit();
                } else if (profileFragment != null && incomingFragment == null) {
                    fragmentManager.beginTransaction()
                            .hide(profileFragment)
                            .add(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                            .commit();
                } else if (profileFragment != null && incomingFragment != null) {
                    fragmentManager.beginTransaction()
                            .hide(profileFragment).hide(incomingFragment)
                            .add(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .add(R.id.home_page_layout, recentFragment, RecentFragment.class.getName())
                            .commit();
                }
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
            incomingFragment = IncomingFragment.newInstance();
            if (recentFragment == null && profileFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(profileFragment)
                        .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
                        .commit();
            } else if (recentFragment != null && profileFragment == null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment)
                        .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
                        .commit();
            } else if (recentFragment != null && profileFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).hide(profileFragment)
                        .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.home_page_layout, incomingFragment, IncomingFragment.class.getName())
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

    private void goToProfileFragment() {
        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();
            if (recentFragment == null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(incomingFragment)
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
                        .commit();
            } else if (recentFragment != null && incomingFragment == null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment)
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
                        .commit();
            } else if (recentFragment != null && incomingFragment != null) {
                fragmentManager.beginTransaction()
                        .hide(recentFragment).hide(incomingFragment)
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.home_page_layout, profileFragment, ProfileFragment.class.getName())
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

    public void goToNetworkDialogFragment() {
        networkDialogFragment = NetworkDialogFragment.newInstance();
        networkDialogFragment.show(fragmentManager.beginTransaction(), NetworkDialogFragment.class.getName());
        networkDialogIsShowing = true;
    }

    // ********************************************************************************
    // Implement

    @Override
    public void goToApp() {
        setValueToBottomNavigation();
    }

    @Override
    public void goToRegisterFragment() {
        registerFragment = RegisterFragment.newInstance();
        fragmentManager.beginTransaction()
                .add(R.id.base_layout, registerFragment, RegisterFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToLoginDialogFragment() {
        loginDialogFragment = LoginDialogFragment.newInstance();
        loginDialogFragment.show(fragmentManager.beginTransaction(), LoginDialogFragment.class.getName());
        loginDialogFragment.setCancelable(false);
    }

    @Override
    public void goToRecentDetailFragment(Product product) {
        recentDetailFragment = RecentDetailFragment.newInstance(product);
        fragmentManager.beginTransaction()

                .add(R.id.base_layout, recentDetailFragment, RecentDetailFragment.class.getName())
                .hide(recentFragment)
                .addToBackStack(RecentDetailFragment.class.getName())
                .commit();
    }

    @Override
    public void goToIncomingDetailFragment(Product product) {
        incomingDetailFragment = IncomingDetailFragment.newInstance(product);
        fragmentManager.beginTransaction()
                .add(R.id.base_layout, incomingDetailFragment, IncomingDetailFragment.class.getName())
                .hide(incomingFragment)
                .addToBackStack(IncomingDetailFragment.class.getName())
                .commit();
    }

    @Override
    public void goToDeleteDialogFragment(Product product) {
        DialogFragment dialogFragment = DeleteDialogFragment.newInstance(product);
        dialogFragment.show(fragmentManager.beginTransaction(), DeleteDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

    @Override
    public void goToReplyDialogFragment(Product product) {
        DialogFragment dialogFragment = ReplyDialogFragment.newInstance(product);
        dialogFragment.show(fragmentManager.beginTransaction(), ReplyDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

    @Override
    public void goToEditTextDialogFragment(int callStatus, int type) {
        DialogFragment dialogFragment = EditTextDialogFragment.newInstance(callStatus, type);
        dialogFragment.show(fragmentManager.beginTransaction(), EditTextDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

    @Override
    public void goToEditCategoryDialogFragment(int callStatus) {
        DialogFragment dialogFragment = EditCategoryDialogFragment.newInstance(callStatus);
        dialogFragment.show(fragmentManager.beginTransaction(), EditCategoryDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

    @Override
    public void setProductToFragmentForDelete(Product product) {
        recentFragment.sendRequestDeleteProduct(product);
    }

    @Override
    public void setProductToFragmentForChangeReplyImage(Product product) {
        recentFragment.changeReplyImage(product);
    }

    @Override
    public void setPriceToRecentDetailFragment(String price, String description) {
        recentDetailFragment.sendReply(price, description);
    }

    @Override
    public void setTextToProfileFragment(String text, int type) {
        profileFragment.sendInfo(text, type);
    }

    @Override
    public void setTextToRegisterFragment(String text, int type) {
        registerFragment.setInfoToTextView(text, type);
    }

    @Override
    public void setLoginInfoToFirstPageFragment(String username, String password) {
        loginDialogFragment.dismiss();
        firstPageFragment.setLoginInfo(username, password);
    }

    @Override
    public void logoutFromApp() {
        try {
            if (VersionUtil.isVersionCodesKitkatSupported()) {
                ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isConnecting() {
        if (networkDialogIsShowing) {
            networkDialogFragment.dismiss();
            networkDialogIsShowing = false;
            switch (bottomNavigation.getSelectedItemId()) {
                case R.id.bottom_navigation_profile:
                    title.setText(R.string.profile);
                    logo.setImageResource(R.drawable.baseline_person_white_24);
                    goToProfileFragment();
                    break;
                case R.id.bottom_navigation_recent:
                    title.setText(R.string.recent);
                    logo.setImageResource(R.drawable.baseline_mail_white_24);
                    goToRecentFragment();
                    break;
                case R.id.bottom_navigation_incoming:
                    title.setText(R.string.incoming);
                    logo.setImageResource(R.drawable.baseline_drafts_white_24);
                    goToIncomingFragment();
                    break;
            }
        }
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.activity_main_toolbar_logout)
    public void onClicLogout() {
        DialogFragment dialogFragment = LogoutDialogFragment.newInstance();
        dialogFragment.show(fragmentManager.beginTransaction(), LogoutDialogFragment.class.getName());
        dialogFragment.setCancelable(false);
    }

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
    protected void onStart() {
        super.onStart();
        if (networkDialogFragment != null) {
            networkDialogIsShowing = true;
            if (FunctionUtil.isConnecting(this)) {
                networkDialogFragment.dismiss();
                networkDialogIsShowing = false;
                goToRecentFragment();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        networkDialogIsShowing = false;
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pref = null;
        fragmentManager = null;
        recentFragment = null;
        incomingFragment = null;
        profileFragment = null;
        recentDetailFragment = null;
        incomingDetailFragment = null;
        networkDialogFragment = null;
        loginDialogFragment = null;
        firstPageFragment = null;
        registerFragment = null;
        networkReceiver = null;
        unbinder.unbind();
        super.onDestroy();
    }
}