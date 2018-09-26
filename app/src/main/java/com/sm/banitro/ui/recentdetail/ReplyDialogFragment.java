package com.sm.banitro.ui.recentdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReplyDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;
    private Product product;

    // Data Type
    private static final String KEY_PRODUCT = "product";

    // ********************************************************************************
    // New Instance

    public static ReplyDialogFragment newInstance(Product product) {
        ReplyDialogFragment fragment = new ReplyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setProduct((Product) bundle.getParcelable(KEY_PRODUCT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_reply, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);
    }

    // ********************************************************************************
    // Method

    public void setProduct(Product product) {
        this.product = product;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_reply_btn_send)
    public void onClickSend() {

    }

    @OnClick(R.id.dialog_fragment_reply_btn_cancel)
    public void onClickCancel() {
        dismiss();
    }
}