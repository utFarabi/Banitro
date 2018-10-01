package com.sm.banitro.ui.home.profile;

import android.content.Context;

import com.sm.banitro.data.model.seller.Seller;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

public class ProfilePresenter implements ProfileContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private ProfileContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public ProfilePresenter(ProfileContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = new Repository(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void loadData() {
        iaView.showProgress();
        repository.loadSeller(new ApiResult<Seller>() {

            @Override
            public void onSuccess(Seller result) {
                iaView.hideProgress();
                iaView.showSeller(result);
            }

            @Override
            public void onFail(String errorMessage) {
                iaView.hideProgress();
                iaView.showErrorMessage(errorMessage);
            }
        });
    }
}