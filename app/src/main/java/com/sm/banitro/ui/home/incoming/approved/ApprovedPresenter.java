package com.sm.banitro.ui.home.incoming.approved;

import android.content.Context;

import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.util.ConstantUtil;

import java.util.ArrayList;

public class ApprovedPresenter implements ApprovedContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private ApprovedContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public ApprovedPresenter(ApprovedContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = Repository.newInstance(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void loadData(int offset) {
        iaView.showProgress();
        repository.loadProducts(ConstantUtil.CONDITION_APPROVED, offset,
                new ApiResult<ArrayList<Product>>() {

                    @Override
                    public void onSuccess(ArrayList<Product> result) {
                        iaView.hideProgress();
                        iaView.showProducts(result);
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        iaView.hideProgress();
                        iaView.showErrorMessage(errorMessage);
                    }
                });
    }
}