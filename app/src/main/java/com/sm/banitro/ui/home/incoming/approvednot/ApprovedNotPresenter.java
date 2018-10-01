package com.sm.banitro.ui.home.incoming.approvednot;

import android.content.Context;

import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.util.Constant;

import java.util.ArrayList;

public class ApprovedNotPresenter implements ApprovedNotContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private ApprovedNotContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public ApprovedNotPresenter(ApprovedNotContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = new Repository(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void loadData() {
        repository.loadProducts(Constant.CONDITION_APPROVED_NOT,
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