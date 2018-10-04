package com.sm.banitro.ui.home.profile;

import com.sm.banitro.data.model.seller.Seller;

public interface ProfileContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showSeller(Seller seller);

        void showErrorMessage(String message);

        void infoSent(String text, int type);
    }

    interface Presenter {

        void loadData();

        void sendInfo(String text, int type);
    }
}