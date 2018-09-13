package com.sm.banitro.ui.recent;

import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

public interface RecentContract {

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