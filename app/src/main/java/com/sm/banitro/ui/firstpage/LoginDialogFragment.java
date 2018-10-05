package com.sm.banitro.ui.firstpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sm.banitro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private Unbinder unbinder;

    // View
    @BindView(R.id.dialog_fragment_login_et_username)
    EditText etUsername;
    @BindView(R.id.dialog_fragment_login_et_password)
    EditText etPassword;

    // ********************************************************************************
    // New Instance

    public static LoginDialogFragment newInstance() {
        LoginDialogFragment fragment = new LoginDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (Interaction) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_login_btn_send)
    public void onClickSend() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (!username.isEmpty() && username.length() == 11 && !password.isEmpty()) {
            interaction.setLoginInfoToFirstPageFragment(username, password);
        }
    }

    @OnClick(R.id.dialog_fragment_login_btn_cancel)
    public void onClickCancel() {
        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interaction = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void setLoginInfoToFirstPageFragment(String username, String password);
    }
}