package com.sm.banitro.ui.home.recent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.banitro.R;
import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecentFragment extends Fragment implements RecentContract.View, RecentAdapter.Interaction {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private RecentContract.Presenter iaPresenter;
    private RecentAdapter recentAdapter;
    private Unbinder unbinder;

    // View
    @BindView(R.id.recent_fragment_rv_recent)
    RecyclerView rvRecent;
    @BindView(R.id.recent_fragment_pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.recent_fragment_tv_no_message_found)
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recent_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        rvRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecent.setItemAnimator(new DefaultItemAnimator());
        rvRecent.setAdapter(recentAdapter);

        iaPresenter.loadData();
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
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProductFromAdapterToRecentFragment(Product product) {
        interaction.goToProductDetailFragment(product);
    }

    @Override
    public void openDeleteDialogFragment(Product product) {
        interaction.goToDeleteDialogFragment(product);
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
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToProductDetailFragment(Product product);

        void goToDeleteDialogFragment(Product product);
    }
}