package com.sm.banitro.ui.home.incoming.approvednot;

import com.sm.banitro.data.model.product.Product;

import java.util.ArrayList;

public interface ApprovedNotContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showProducts(ArrayList<Product> products);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void loadData();
    }
}