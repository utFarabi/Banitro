package com.sm.banitro.ui.home.incoming;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sm.banitro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IncomingFragment extends Fragment {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;
    private PagerAdapter pagerAdapter;

    // View
    @BindView(R.id.fragment_incoming_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.fragment_incoming_view_page)
    ViewPager viewPager;

    // ********************************************************************************
    // New Instance

    public static IncomingFragment newInstance() {
        IncomingFragment fragment = new IncomingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new PagerAdapter(getFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_incoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setValueToTabLayout();
    }

    // ********************************************************************************
    // Initialization

    private void setValueToTabLayout(){
        tabLayout.getTabAt(0)
                .setText(R.string.approved)
                .setIcon(R.drawable.baseline_check_circle_outline_white_24);
        tabLayout.getTabAt(0).getIcon().setColorFilter(
                ContextCompat.getColor(getContext(), R.color.colorSelected),
                PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(1)
                .setText(R.string.approved_not)
                .setIcon(R.drawable.baseline_highlight_off_white_24);
        tabLayout.getTabAt(1).getIcon().setColorFilter(
                ContextCompat.getColor(getContext(), R.color.colorUnselected),
                PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon()
                        .setColorFilter(
                                ContextCompat.getColor(getContext(), R.color.colorSelected),
                                PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon()
                        .setColorFilter(
                                ContextCompat.getColor(getContext(), R.color.colorUnselected),
                                PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // ********************************************************************************
    // Supplementary Override

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pagerAdapter = null;
    }
}