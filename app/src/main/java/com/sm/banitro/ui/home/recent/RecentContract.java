package com.sm.banitro.ui.home.recent;

import com.sm.banitro.data.model.product.Product;

import java.util.ArrayList;

public interface RecentContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showProducts(ArrayList<Product> products);

        void showErrorMessage(String message);

        void productDeleted(Product product);
    }

    interface Presenter {

        void loadData();

        void deleteProduct(Product product);
    }
}