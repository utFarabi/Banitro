package com.sm.banitro.ui.register;

import android.content.Context;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

public class RegisterPresenter implements RegisterContract.Presenter {

    // ********************************************************************************
    // Field

    // Instance
    private RegisterContract.View iaView;
    private Repository repository;

    // ********************************************************************************
    // Constructor

    public RegisterPresenter(RegisterContract.View iaView, Context context) {
        this.iaView = iaView;
        repository = new Repository(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void sendRegisterInfo(String name, String phoneNamber, String address, String categories) {
        iaView.showProgress();
        repository.sendRegisterInfo(name, phoneNamber, address, categories,
                new ApiResult<BaseResponse>() {

                    @Override
                    public void onSuccess(BaseResponse result) {
                        iaView.hideProgress();
                        if (result.isResult()) {
                            iaView.registerFinished();
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