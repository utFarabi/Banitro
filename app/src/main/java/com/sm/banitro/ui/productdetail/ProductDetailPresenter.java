package com.sm.banitro.ui.productdetail;

import android.content.Context;

import com.sm.banitro.data.source.remote.Repository;

public class ProductDetailPresenter implements ProductDetailContract.Presenter {
    private ProductDetailContract.View iaView;
//    private Repository repository;

    public ProductDetailPresenter(ProductDetailContract.View iaView, Context context) {
        this.iaView = iaView;
//        repository = new Repository(context);
    }
}