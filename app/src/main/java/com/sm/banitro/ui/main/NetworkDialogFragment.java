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

public class NetworkDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button btnMobile, btnWiFi;

    public static NetworkDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        NetworkDialogFragment fragment = new NetworkDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

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
        btnMobile = view.findViewById(R.id.network_dialog_fragment_btn_mobile);
        btnWiFi = view.findViewById(R.id.network_dialog_fragment_btn_wifi);
        btnMobile.setOnClickListener(this);
        btnWiFi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.network_dialog_fragment_btn_mobile:
                startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                break;
            case R.id.network_dialog_fragment_btn_wifi:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
        }
    }
}