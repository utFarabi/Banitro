package com.sm.banitro.ui.home.incoming.approved;

import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

public interface ApprovedContract {

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