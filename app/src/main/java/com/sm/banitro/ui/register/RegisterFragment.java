package com.sm.banitro.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.source.local.AppPreferences;
import com.sm.banitro.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends Fragment implements RegisterContract.View {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private RegisterContract.Presenter iaPresenter;
    private AppPreferences pref;
    private Unbinder unbinder;

    // View
    @BindView(R.id.fragment_register_tv_name)
    TextView tvName;
    @BindView(R.id.fragment_register_tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.fragment_register_tv_address)
    TextView tvAddress;
    @BindView(R.id.fragment_register_tv_categories)
    TextView tvCategories;
    @BindView(R.id.fragment_register_pb_progress)
    ProgressBar pbProgress;

    // ********************************************************************************
    // New Instance

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        iaPresenter = new RegisterPresenter(this, getContext());
        pref = new AppPreferences(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);
    }

    // ********************************************************************************
    // Method

    public void setInfoToTextView(String text, int type) {
        switch (type) {
            case R.string.full_name:
                tvName.setText(text);
                break;
            case R.string.phone:
                tvPhoneNumber.setText(text);
                break;
            case R.string.address:
                tvAddress.setText(text);
                break;
            case R.string.categories:
                tvCategories.setText(getCategoriesName(text));
                break;
        }
    }

    private String getCategoriesName(String text) {
        String result="";
        while (!text.isEmpty()){
            switch (text.substring(0,2)){
                case Constant.CATEGORY_10:
                    result+=getString(R.string.category_10)+" . ";
                    break;
                case Constant.CATEGORY_12:
                    result+=getString(R.string.category_12)+" . ";
                    break;
                    case Constant.CATEGORY_14:
                    result+=getString(R.string.category_14)+" . ";
                    break;
                    case Constant.CATEGORY_16:
                    result+=getString(R.string.category_16)+" . ";
                    break;
                    case Constant.CATEGORY_18:
                    result+=getString(R.string.category_18)+" . ";
                    break;
                    case Constant.CATEGORY_20:
                    result+=getString(R.string.category_20)+" . ";
                    break;
                    case Constant.CATEGORY_22:
                    result+=getString(R.string.category_22)+" . ";
                    break;
                    case Constant.CATEGORY_24:
                    result+=getString(R.string.category_24)+" . ";
                    break;
                    case Constant.CATEGORY_26:
                    result+=getString(R.string.category_26)+" . ";
                    break;
                    case Constant.CATEGORY_28:
                    result+=getString(R.string.category_28)+" . ";
                    break;
            }
            text=text.substring(2);
        }
        return result;
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
    public void registerFinished() {
        pref.setFirstLogin(false);
        interaction.goToApp();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.fragment_register_cl_name)
    public void onClickName() {
        interaction.goToEditTextDialogFragment(Constant.REGISTER_DIALOG, R.string.full_name);
    }

    @OnClick(R.id.fragment_register_cl_phoneNumber)
    public void onClickPhoneNumber() {
        interaction.goToEditTextDialogFragment(Constant.REGISTER_DIALOG, R.string.phone);
    }

    @OnClick(R.id.fragment_register_cl_address)
    public void onClickAddress() {
        interaction.goToEditTextDialogFragment(Constant.REGISTER_DIALOG, R.string.address);
    }

    @OnClick(R.id.fragment_register_cl_categories)
    public void onClickCategory() {
        interaction.goToEditCategoryDialogFragment(Constant.REGISTER_DIALOG);
    }

    @OnClick(R.id.fragment_register_btn_send)
    public void onClickSend() {
        String name = tvName.getText().toString();
        String phoneNumber = tvPhoneNumber.getText().toString();
        String address = tvAddress.getText().toString();
        String categories = tvCategories.getText().toString();
        if (!name.isEmpty() && !phoneNumber.isEmpty() &&
                !address.isEmpty() && !categories.isEmpty()) {
            iaPresenter.sendRegisterInfo(name, phoneNumber, address, categories);
        }
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

        void goToEditTextDialogFragment(int callStatus, int type);

        void goToEditCategoryDialogFragment(int callStatus);
    }
}