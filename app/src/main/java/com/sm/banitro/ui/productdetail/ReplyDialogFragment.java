package com.sm.banitro.ui.productdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;

public class ReplyDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String KEY_PRODUCT = "product";
    private Product product;
    private Button btnSend,btnCancel;

    public static ReplyDialogFragment newInstance(Product product) {
        ReplyDialogFragment fragment = new ReplyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }

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

        return inflater.inflate(R.layout.reply_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSend=view.findViewById(R.id.reply_dialog_fragment_btn_send);
        btnCancel=view.findViewById(R.id.reply_dialog_fragment_btn_cancel);
        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reply_dialog_fragment_btn_send:

                break;
            case R.id.reply_dialog_fragment_btn_cancel:
                dismiss();
                break;
        }
    }
}