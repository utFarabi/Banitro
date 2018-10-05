package com.sm.banitro.ui.firstpage;

import android.content.Context;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

public class FirstPagePresenter implements FirstPageContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private FirstPageContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public FirstPagePresenter(FirstPageContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = new Repository(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void sendLoginInfo(String username, String password) {
        iaView.showProgress();
        repository.sendLoginInfo(username, password,
                new ApiResult<String>() {

                    @Override
                    public void onSuccess(String result) {
                        iaView.hideProgress();
                        iaView.loginFinished(result);
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        iaView.hideProgress();
                        iaView.showErrorMessage(errorMessage);
                    }
                });
    }
}