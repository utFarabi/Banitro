package com.sm.banitro.ui.recentdetail;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.util.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecentDetailFragment extends Fragment implements RecentDetailContract.View {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private RecentDetailContract.Presenter iaPresenter;
    private Product product;
    private Unbinder unbinder;

    // Seller Type
    private static final String KEY_PRODUCT = "product";

    // View
    @BindView(R.id.fragment_recent_detail_iv_picture)
    ImageView ivPicture;
    @BindView(R.id.fragment_recent_detail_tv_name)
    TextView tvName;
    @BindView(R.id.fragment_recent_detail_tv_category)
    TextView tvCategory;
    @BindView(R.id.fragment_recent_detail_tv_number)
    TextView tvNumber;
    @BindView(R.id.fragment_recent_detail_btn_submit_price)
    Button btnSendPrice;
    @BindView(R.id.fragment_recent_detail_pb_progress)
    ProgressBar pbProgress;

    // ********************************************************************************
    // New Instance

    public static RecentDetailFragment newInstance(Product product) {
        RecentDetailFragment fragment = new RecentDetailFragment();
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
        iaPresenter = new RecentDetailPresenter(this, getContext());
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setProduct((Product) bundle.getParcelable(KEY_PRODUCT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recent_detail, container, false);
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
        if (product.isReplied()) {
            btnSendPrice.setText(
                    Function.convertIntToStrMoney(Integer.parseInt(product.getReplyPrice()), false)
                            + "   :" + getString(R.string.suggested_price));
        }
        if (product.getProPicture() != null && !product.getProPicture().isEmpty()) {
            Glide.with(this).load(product.getProPicture()).into(ivPicture);
        }
    }

    // ********************************************************************************
    // Method

    public void setProduct(Product product) {
        this.product = product;
    }

    public void sendReply(String price, String description) {
        iaPresenter.sendReply(product, price, description);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void replySent(String price, String description) {
        product.setReplyPrice(price);
        product.setReplyDc(description);
        btnSendPrice.setText(Function.convertIntToStrMoney(Integer.parseInt(product.getReplyPrice()), false)
                + "   :" + getString(R.string.suggested_price));
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.fragment_recent_detail_btn_submit_price)
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