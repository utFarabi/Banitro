package com.sm.banitro.ui.home.recent;

import android.content.Context;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.util.ConstantUtil;

import java.util.ArrayList;

public class RecentPresenter implements RecentContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private RecentContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public RecentPresenter(RecentContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = Repository.newInstance(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void loadData(int offset) {
        iaView.showProgress();
        repository.loadProducts(ConstantUtil.CONDITION_RECENT, offset,
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

    @Override
    public void deleteProduct(final Product product) {
        iaView.showProgress();
        repository.deleteProduct(product, new ApiResult<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse result) {
                iaView.hideProgress();
                if (result.isResult()) {
                    iaView.productDeleted(product);
                }
            }

            @Override
            public void onFail(String errorMessage) {
                iaView.hideProgress();
                iaView.showErrorMessage(errorMessage);
            }
        });
    }
}