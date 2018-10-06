package com.sm.banitro.ui.home.recent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.ui.main.PaginationScrollListener;
import com.sm.banitro.util.FunctionUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecentFragment extends Fragment
        implements RecentContract.View, RecentAdapter.Interaction {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private RecentContract.Presenter iaPresenter;
    private RecentAdapter recentAdapter;
    private LinearLayoutManager layoutManager;
    private Unbinder unbinder;

    // Data Type
    private boolean isLoading;

    // View
    @BindView(R.id.fragment_recent_rv_recent)
    RecyclerView rvRecent;
    @BindView(R.id.fragment_recent_pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.fragment_recent_tv_no_message_found)
    TextView tvNotFound;

    // ********************************************************************************
    // New Instance

    public static RecentFragment newInstance() {
        RecentFragment fragment = new RecentFragment();
        Bundle bundle = new Bundle();
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
        iaPresenter = new RecentPresenter(this, getContext());
        recentAdapter = new RecentAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        rvRecent.setLayoutManager(layoutManager);
        rvRecent.setAdapter(recentAdapter);

        iaPresenter.loadData();

        rvRecent.addOnScrollListener(new PaginationScrollListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                if (FunctionUtil.isConnecting(getContext())) {
                    isLoading = true;
                    iaPresenter.loadData();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
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
    public void showProducts(ArrayList<Product> products) {
        if (products == null || products.size() == 0) {
            tvNotFound.setVisibility(View.VISIBLE);
        } else {
            recentAdapter.setProducts(products);
        }

        isLoading = false;
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void productDeleted(Product product) {
        recentAdapter.deleteProduct(product);
    }

    @Override
    public void setProductFromAdapterToRecentFragment(Product product) {
        interaction.goToRecentDetailFragment(product);
    }

    @Override
    public void openDeleteDialogFragment(Product product) {
        interaction.goToDeleteDialogFragment(product);
    }

    // ********************************************************************************
    // Method

    public void sendRequestDeleteProduct(Product product) {
        iaPresenter.deleteProduct(product);
    }

    // ********************************************************************************
    // Supplementary Override

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
        recentAdapter = null;
        layoutManager = null;
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToRecentDetailFragment(Product product);

        void goToDeleteDialogFragment(Product product);
    }
}