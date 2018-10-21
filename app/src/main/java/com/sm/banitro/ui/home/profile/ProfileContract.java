package com.sm.banitro.ui.home.profile;

import com.sm.banitro.data.model.seller.Seller;

import java.io.File;

public interface ProfileContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showSeller(Seller seller);

        void showErrorMessage(String message);

        void infoSent(String text, int type);

        void imageSent(String path);
    }

    interface Presenter {

        void loadData();

        void sendInfo(String text, int type);

        void sendImage(File file);
    }
}