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

import com.bumptech.glide.Glide;
import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.util.ConstantUtil;
import com.sm.banitro.util.FunctionUtil;

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
    private final String IMAGE_DEFAULT = "NULL";

    // View
    @BindView(R.id.fragment_incoming_detail_iv_picture)
    ImageView ivPicture;
    @BindView(R.id.fragment_incoming_detail_tv_name)
    TextView tvName;
    @BindView(R.id.fragment_incoming_detail_tv_category)
    TextView tvCategory;
    @BindView(R.id.fragment_incoming_detail_tv_model)
    TextView tvModel;
    @BindView(R.id.fragment_incoming_detail_tv_year)
    TextView tvYear;
    @BindView(R.id.fragment_incoming_detail_tv_number)
    TextView tvNumber;
    @BindView(R.id.fragment_incoming_detail_tv_description)
    TextView tvDescription;
    @BindView(R.id.fragment_incoming_detail_tv_price)
    TextView tvPrice;
    @BindView(R.id.fragment_incoming_detail_tv_condition)
    TextView tvCondition;
    @BindView(R.id.fragment_incoming_detail_tv_chassis)
    TextView tvChassis;

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
        if (product.getProPicture() != null && !product.getProPicture().equals(IMAGE_DEFAULT)) {
            Glide.with(this).load(product.getProPicture()).into(ivPicture);
        }
        tvName.setText(product.getProName());
        tvCategory.setText(FunctionUtil.getCategoryName(product.getProCat()));
        tvModel.setText(product.getCarCo() + "  " + product.getCarMo());
        tvYear.setText(product.getCarYear());
        tvNumber.setText(String.valueOf(product.getProNumber()));
        if (product.getProductDc() != null) {
            tvDescription.setText(product.getProductDc());
        } else {
            tvDescription.setText("—");
        }
        if (product.getReplyPrice()!=null) {
            tvPrice.setText(FunctionUtil.convertIntToStrMoney(Integer.parseInt(product.getReplyPrice()), false));
        }else {
            tvPrice.setText("—");
        }
        if (product.getPosition().equals(ConstantUtil.CONDITION_APPROVED)) {
            tvCondition.setText(R.string.approved);
        } else if (product.getPosition().equals(ConstantUtil.CONDITION_APPROVED_NOT)) {
            tvCondition.setText(R.string.approved_not);
        }
        if (product.getCarSpid()!=null){
            tvChassis.setText(product.getCarSpid());
        }else {
            tvChassis.setText("—");
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