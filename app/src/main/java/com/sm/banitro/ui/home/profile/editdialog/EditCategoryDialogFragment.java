package com.sm.banitro.ui.home.profile.editdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sm.banitro.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditCategoryDialogFragment extends DialogFragment implements EditCategoryAdapter.Interaction {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;
    private EditCategoryAdapter editCategoryAdapter;
    private ArrayList<String> categories;
    private ArrayList<String> myCategories;
    private boolean[] checks;

    // View
    @BindView(R.id.dialog_fragment_category_rv_category)
    RecyclerView rvCategory;

    // ********************************************************************************
    // New Instance

    public static EditCategoryDialogFragment newInstance() {
        EditCategoryDialogFragment fragment = new EditCategoryDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editCategoryAdapter = new EditCategoryAdapter(this);
        categories = new ArrayList<>();
        myCategories = new ArrayList<>();
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

        setValueToCategories();
    }

    // ********************************************************************************
    // Initialization

    public void setValueToCategories() {
        categories.add("دسته بندی 0");
        categories.add("دسته بندی 1");
        categories.add("دسته بندی 2");
        categories.add("دسته بندی 3");
        categories.add("دسته بندی 4");
        categories.add("دسته بندی 5");
        categories.add("دسته بندی 6");
        categories.add("دسته بندی 7");
        categories.add("دسته بندی 8");
        categories.add("دسته بندی 9");
        editCategoryAdapter.setCategories(categories);
        checks = new boolean[categories.size()];
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
                myCategories.add(categories.get(i));
            }
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
        myCategories = null;
        checks = null;
    }
}