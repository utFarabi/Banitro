package com.sm.banitro.ui.editdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sm.banitro.R;
import com.sm.banitro.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditCategoryDialogFragment extends DialogFragment implements EditCategoryAdapter.Interaction {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private Unbinder unbinder;
    private EditCategoryAdapter editCategoryAdapter;
    private ArrayList<String> categories;
    private boolean[] checks;

    // View
    @BindView(R.id.dialog_fragment_category_rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.dialog_fragment_category_btn_send)
    Button btnSend;

    // Data Type
    private static final String KEY_CALL_STATUS = "callStatus";
    private int callStatus;
    private String categoriesCode;

    // ********************************************************************************
    // New Instance

    public static EditCategoryDialogFragment newInstance(int callStatus) {
        EditCategoryDialogFragment fragment = new EditCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CALL_STATUS, callStatus);
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

        Bundle bundle = getArguments();
        if (bundle == null) return;
        setCallStatus(bundle.getInt(KEY_CALL_STATUS));

        editCategoryAdapter = new EditCategoryAdapter(this);
        categories = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.setAdapter(editCategoryAdapter);
        if (callStatus == Constant.REGISTER_DIALOG) {
            btnSend.setText(R.string.approve);
        }

        // Init Data Type
        categoriesCode = "";

        setValueToCategories();
    }

    // ********************************************************************************
    // Initialization

    public void setValueToCategories() {
        categories.add(getString(R.string.category_10));
        categories.add(getString(R.string.category_12));
        categories.add(getString(R.string.category_14));
        categories.add(getString(R.string.category_16));
        categories.add(getString(R.string.category_18));
        categories.add(getString(R.string.category_20));
        categories.add(getString(R.string.category_22));
        categories.add(getString(R.string.category_24));
        categories.add(getString(R.string.category_26));
        categories.add(getString(R.string.category_28));
        editCategoryAdapter.setCategories(categories);
        checks = new boolean[categories.size()];
    }

    // ********************************************************************************
    // Method

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    // ********************************************************************************
    // Implement

    @Override
    public void onCheckedChanged(int position, boolean isChecked) {
        checks[position] = isChecked;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_category_btn_send)
    public void onClickSend() {
        for (int i = 0; i < categories.size(); i++) {
            if (checks[i]) {
                categoriesCode += String.valueOf((i + 5) * 2);
            }
        }
        if (!categoriesCode.isEmpty()) {
            if (callStatus == Constant.EDIT_DIALOG) {
                interaction.setTextToProfileFragment(categoriesCode, R.string.categories);
            } else if (callStatus == Constant.REGISTER_DIALOG) {
                interaction.setTextToRegisterFragment(categoriesCode, R.string.categories);
            }
            dismiss();
        }
    }

    @OnClick(R.id.dialog_fragment_category_btn_cancel)
    public void onClickCancel() {
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editCategoryAdapter = null;
        categories = null;
        checks = null;
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

        void setTextToProfileFragment(String text, int type);

        void setTextToRegisterFragment(String text, int type);
    }
}