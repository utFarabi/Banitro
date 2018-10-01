package com.sm.banitro.ui.recentdetail;

import com.sm.banitro.data.model.product.Product;

public interface RecentDetailContract {

    interface View {

        void showProgress();

        void hideProgress();

        void replySent(String price, String description);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void sendReply(Product product, String price, String description);
    }
}