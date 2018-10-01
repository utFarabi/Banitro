package com.sm.banitro.ui.recentdetail;

import android.content.Context;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

public class RecentDetailPresenter implements RecentDetailContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private RecentDetailContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public RecentDetailPresenter(RecentDetailContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = new Repository(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void sendReply(final Product product, final String price, final String description) {
        iaView.showProgress();
        repository.sendReply(product, price, description,
                new ApiResult<BaseResponse>() {

                    @Override
                    public void onSuccess(BaseResponse result) {
                        iaView.hideProgress();
                        if (result.isResult()) {
                            iaView.replySent(price, description);
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