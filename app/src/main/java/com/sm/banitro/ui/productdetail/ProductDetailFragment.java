package com.sm.banitro.ui.productdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.util.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private ProductDetailContract.Presenter iaPresenter;
    private Product product;
    private Unbinder unbinder;

    // Data Type
    private static final String KEY_PRODUCT = "product";

    // View
    @BindView(R.id.product_detail_fragment_tv_name)
    TextView tvName;
    @BindView(R.id.product_detail_fragment_tv_category)
    TextView tvCategory;
    @BindView(R.id.product_detail_fragment_tv_number)
    TextView tvNumber;
    @BindView(R.id.product_detail_fragment_iv_picture)
    ImageView ivPicture;
    @BindView(R.id.product_detail_fragment_btn_submit_price)
    Button btnSendPrice;

    // ********************************************************************************
    // New Instance

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iaPresenter = new ProductDetailPresenter(this, getContext());
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

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        tvName.setText(product.getName());
        tvCategory.setText(product.getCategory().getName());
        tvNumber.setText(String.valueOf(product.getNumber()));
        if (product.isReplied()) {
            btnSendPrice.setText(
                    Function.convertIntToStrMoney(product.getReply().getPrice(), false)
                            + "   :" + getString(R.string.suggested_price));
        }
//        Glide.with(this).load(Constant.BASE_URL + product.getPicture()).into(ivPicture);
    }

    // ********************************************************************************
    // Method

    public void setProduct(Product product) {
        this.product = product;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.product_detail_fragment_btn_submit_price)
    public void onClickSubmitPrice() {
        interaction.goToReplyDialogFragment(product);
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
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToReplyDialogFragment(Product product);
    }
}