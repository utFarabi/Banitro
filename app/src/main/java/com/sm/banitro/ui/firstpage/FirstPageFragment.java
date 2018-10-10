package com.sm.banitro.ui.firstpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.source.local.AppPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FirstPageFragment extends Fragment implements FirstPageContract.View {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private FirstPageContract.Presenter iaPresenter;
    private AppPreferences pref;
    private Unbinder unbinder;

    // View
    @BindViews({R.id.fragment_first_page_tv_b, R.id.fragment_first_page_tv_a,
            R.id.fragment_first_page_tv_n, R.id.fragment_first_page_tv_i,
            R.id.fragment_first_page_tv_t, R.id.fragment_first_page_tv_r,
            R.id.fragment_first_page_tv_o})
    List<TextView> tvLogo;
    @BindView(R.id.fragment_first_page_pb_progress)
    ProgressBar pbProgress;

    // ********************************************************************************
    // New Instance

    public static FirstPageFragment newInstance() {
        FirstPageFragment fragment = new FirstPageFragment();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iaPresenter = new FirstPagePresenter(this, getContext());
        pref = new AppPreferences(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        setValueToTextViewAnim();
    }

    // ********************************************************************************
    // Method

    private void setValueToTextViewAnim(){
        for (int i = 0; i < tvLogo.size(); i++) {
            int time=500;
            Animation anim = new AlphaAnimation(1.0f, 0.1f);
            anim.setDuration((tvLogo.size()-i)*time);
            anim.setStartOffset(time*i);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            tvLogo.get(i).startAnimation(anim);
        }
    }

    public void setLoginInfo(String username, String password) {
        iaPresenter.sendLoginInfo(username, password);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void loginFinished(String sellerId) {
        if (sellerId != null && !sellerId.isEmpty()) {
            pref.setFirstLogin(false);
            pref.setSellerId(sellerId);
            Log.i("sina", "sellerId: " + pref.getSellerId());
            interaction.goToApp();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.fragment_first_page_btn_register)
    public void onClickRegister() {
        interaction.goToRegisterFragment();
    }

    @OnClick(R.id.fragment_first_page_btn_login)
    public void onClickLogin() {
        interaction.goToLoginDialogFragment();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        iaPresenter = null;
        pref = null;
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToApp();

        void goToRegisterFragment();

        void goToLoginDialogFragment();
    }
}