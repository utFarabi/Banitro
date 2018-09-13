package com.sm.banitro.ui.home.profile;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View iaView;

    public ProfilePresenter(ProfileContract.View iaView) {
        this.iaView = iaView;
    }
}