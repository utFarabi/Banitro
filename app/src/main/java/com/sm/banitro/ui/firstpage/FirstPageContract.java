package com.sm.banitro.ui.firstpage;

public interface FirstPageContract {

    interface View {

        void showProgress();

        void hideProgress();

        void loginFinished(String sellerId);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void sendLoginInfo(String username, String password);
    }
}