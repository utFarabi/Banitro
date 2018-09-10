package com.sm.banitro.ui.productdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sm.banitro.R;
import com.sm.banitro.data.model.Demand;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.util.Constant;

public class ProductDetailFragment extends Fragment
        implements ProductDetailContract.View, View.OnClickListener {

    private static final String KEY_DEMAND = "demand";
    private Demand demand;
    private TextView tvName, tvCategory, tvNumber;
    private ImageView ivPicture;
    private ProgressBar pbProgress;
    private ProductDetailContract.Presenter iaPresenter;

    public static ProductDetailFragment newInstance(Demand demand) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEMAND, demand);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iaPresenter = new ProductDetailPresenter(this);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setDemand((Demand) bundle.getParcelable(KEY_DEMAND));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.product_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.product_detail_fragment_tv_name);
        tvCategory = view.findViewById(R.id.product_detail_fragment_tv_category);
        tvNumber = view.findViewById(R.id.product_detail_fragment_tv_number);
        ivPicture = view.findViewById(R.id.product_detail_fragment_iv_picture);
        pbProgress = view.findViewById(R.id.product_detail_fragment_pb_progress);
        tvName.setText(demand.getProductName());
        tvCategory.setText(demand.getProductCategory());
        iaPresenter.loadData(demand.getProductId());
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    @Override
    public void onClick(View view) {
        // send price
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void showProduct(Product product) {
        tvNumber.setText(String.valueOf(product.getNumber()));
        Glide.with(this).load(Constant.BASE_URL + product.getPicture()).into(ivPicture);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}