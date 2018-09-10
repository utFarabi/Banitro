package com.sm.banitro.ui.productdetail;

import com.sm.banitro.data.model.Product;

public interface ProductDetailContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showProduct(Product product);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void loadData(int productId);
    }
}