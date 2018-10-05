package com.sm.banitro.ui.firstpage.register;

public interface RegisterContract {

    interface View {

        void showProgress();

        void hideProgress();

        void registerFinished(String sellerId);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void sendRegisterInfo(String name,
                              String phoneNamber,
                              String address,
                              String categories,
                              String password);
    }
}