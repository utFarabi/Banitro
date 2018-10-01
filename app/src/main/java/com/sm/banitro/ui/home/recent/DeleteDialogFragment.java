package com.sm.banitro.ui.home.recent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeleteDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;
    private Interaction interaction;
    private Product product;

    // Data Type
    private static final String KEY_PRODUCT = "product";

    // View
    @BindView(R.id.dialog_fragment_delete_tv_product_name)
    TextView tvProductName;

    // ********************************************************************************
    // New Instance

    public static DeleteDialogFragment newInstance(Product product) {
        DeleteDialogFragment fragment = new DeleteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
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
        setProduct((Product) bundle.getParcelable(KEY_PRODUCT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_delete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        tvProductName.setText(product.getProName());
    }

    // ********************************************************************************
    // Method

    public void setProduct(Product product) {
        this.product = product;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_delete_btn_delete)
    public void onClickDelete() {
        interaction.setProductToFragmentForDelete(product);
        dismiss();
    }

    @OnClick(R.id.dialog_fragment_delete_btn_cancel)
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

        void setProductToFragmentForDelete(Product product);
    }
}