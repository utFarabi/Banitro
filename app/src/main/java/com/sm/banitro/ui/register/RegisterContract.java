package com.sm.banitro.ui.register;

public interface RegisterContract {

    interface View {

        void showProgress();

        void hideProgress();

        void registerFinished();

        void showErrorMessage(String message);
    }

    interface Presenter {

        void sendRegisterInfo(String name, String phoneNamber, String address, String categories);
    }
}