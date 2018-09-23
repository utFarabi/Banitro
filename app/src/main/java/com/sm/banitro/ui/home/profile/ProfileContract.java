package com.sm.banitro.ui.home.profile;

import com.sm.banitro.data.model.Seller;

import java.io.File;

public interface ProfileContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showSeller(Seller seller);

        void showErrorMessage(String message);

        void showSellerImage(File file);
    }

    interface Presenter {

        void loadData();

        void uploadSellerImage(File file);
    }
}