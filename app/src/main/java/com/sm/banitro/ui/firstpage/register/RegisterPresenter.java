package com.sm.banitro.ui.firstpage.register;

import android.content.Context;
import android.util.Log;

import com.sm.banitro.data.model.register.RegisterResponse;
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
        repository = Repository.getINSTANCE(context);
    }

    // ********************************************************************************
    // Implement

    @Override
    public void sendRegisterInfo(String name, String phoneNamber, String address, String categories, String password) {
        iaView.showProgress();
        repository.sendRegisterInfo(name, phoneNamber, address, categories, password,
                new ApiResult<RegisterResponse>() {

                    @Override
                    public void onSuccess(RegisterResponse result) {
                        iaView.hideProgress();
                        if (result.isResult()) {
                            iaView.registerFinished(result.getMessage());
                        }else {
                            iaView.showErrorMessage("ثبت نام با موفقیت انجام نشد.");
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