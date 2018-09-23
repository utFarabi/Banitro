package com.sm.banitro.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sm.banitro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NetworkDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;

    // ********************************************************************************
    // New Instance

    public static NetworkDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        NetworkDialogFragment fragment = new NetworkDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.network_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.network_dialog_fragment_btn_mobile)
    public void onClickMobile() {
        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
    }

    @OnClick(R.id.network_dialog_fragment_btn_wifi)
    public void onClickWifi() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}