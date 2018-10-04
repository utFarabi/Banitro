package com.sm.banitro.ui.incomingdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.util.Constant;
import com.sm.banitro.util.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IncomingDetailFragment extends Fragment {

    // ********************************************************************************
    // Field

    // Instance
    private Product product;
    private Unbinder unbinder;

    // Data Type
    private static final String KEY_PRODUCT = "product";

    // View
    @BindView(R.id.fragment_incoming_detail_iv_picture)
    ImageView ivPicture;
    @BindView(R.id.fragment_incoming_detail_tv_name)
    TextView tvName;
    @BindView(R.id.fragment_incoming_detail_tv_category)
    TextView tvCategory;
    @BindView(R.id.fragment_incoming_detail_tv_number)
    TextView tvNumber;
    @BindView(R.id.fragment_incoming_detail_tv_price)
    TextView tvPrice;
    @BindView(R.id.fragment_incoming_detail_tv_condition)
    TextView tvCondition;

    // ********************************************************************************
    // New Instance

    public static IncomingDetailFragment newInstance(Product product) {
        IncomingDetailFragment fragment = new IncomingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setProduct((Product) bundle.getParcelable(KEY_PRODUCT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_incoming_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        tvName.setText(product.getProName());
        tvCategory.setText(Function.getCategoryName(product.getProCat()));
        tvNumber.setText(String.valueOf(product.getProNumber()));
        tvPrice.setText(Function.convertIntToStrMoney(Integer.parseInt(product.getReplyPrice()), false));
        if (product.getPosition().equals(Constant.CONDITION_APPROVED)) {
            tvCondition.setText(R.string.approved);
        } else if (product.getPosition().equals(Constant.CONDITION_APPROVED_NOT)) {
            tvCondition.setText(R.string.approved_not);
        }
    }

    // ********************************************************************************
    // Method

    public void setProduct(Product product) {
        this.product = product;
    }

    // ********************************************************************************
    // Supplementary Override

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}