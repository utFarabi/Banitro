package com.sm.banitro.ui.productdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.ui.home.recent.RecentFragment;
import com.sm.banitro.util.Constant;

public class ProductDetailFragment extends Fragment
        implements ProductDetailContract.View, View.OnClickListener {

    private static final String KEY_PRODUCT = "product";
    private Product product;
    private TextView tvName, tvCategory, tvNumber;
    private ImageView ivPicture;
    private Button btnSendPrice;
    private ProductDetailContract.Presenter iaPresenter;
    private Interaction interaction;

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (Interaction) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iaPresenter = new ProductDetailPresenter(this,getContext());
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setProduct((Product) bundle.getParcelable(KEY_PRODUCT));
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
        btnSendPrice = view.findViewById(R.id.product_detail_fragment_btn_send_price);
        tvName.setText(product.getName());
        tvCategory.setText(product.getCategory().getName());
        tvNumber.setText(String.valueOf(product.getNumber()));
//        Glide.with(this).load(Constant.BASE_URL + product.getPicture()).into(ivPicture);
        btnSendPrice.setOnClickListener(this);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onClick(View view) {
        interaction.goToReplyDialogFragment(product);
    }

    public interface Interaction {

        void goToReplyDialogFragment(Product product);
    }
}