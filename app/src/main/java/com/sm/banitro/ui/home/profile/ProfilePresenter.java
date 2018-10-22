package com.sm.banitro.ui.home.profile;

import android.content.Context;
import android.util.Log;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.seller.Seller;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        repository = Repository.getINSTANCE(context);
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

    @Override
    public void sendInfo(final String text, final int type) {
        iaView.showProgress();
        repository.sendInfo(text,type,new ApiResult<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse result) {
                iaView.hideProgress();
                if (result.isResult()) {
                    iaView.infoSent(text, type);
                }
            }

            @Override
            public void onFail(String errorMessage) {
                iaView.hideProgress();
                iaView.showErrorMessage(errorMessage);
            }
        });
    }

    @Override
    public void sendImage(final File file) {

        iaView.showProgress();
        repository.sendImage(file,new ApiResult<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse result) {
                iaView.hideProgress();
                if (result.isResult()) {
                    iaView.imageSent(file.getPath());
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