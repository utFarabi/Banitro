package com.sm.banitro.ui.home.incoming.approvednot;

import android.content.Context;

import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.model.Reply;
import com.sm.banitro.util.Constant;

import java.util.ArrayList;

public class ApprovedNotPresenter implements ApprovedNotContract.Presenter {
    private ApprovedNotContract.View iaView;
//    private Repository repository;

    public ApprovedNotPresenter(ApprovedNotContract.View iaView, Context context) {
        this.iaView = iaView;
//        repository = new Repository(context);
    }

    @Override
    public void loadData() {
//        repository.loadProducts(Constant.POSITION_APPROVED_NOT,
//                new ApiResult<ArrayList<Product>>() {
//
//                    @Override
//                    public void onSuccess(ArrayList<Product> result) {
//                        iaView.hideProgress();
//                        iaView.showProducts(result);
//                    }
//
//                    @Override
//                    public void onFail(String errorMessage) {
//                        iaView.hideProgress();
//                        iaView.showErrorMessage(errorMessage);
//                    }
//                });
        test();
    }

    private void test() {
        ArrayList<Product> products = new ArrayList<>();

        Product product = new Product();
        Category category = new Category();
        Reply reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setPosition(Constant.POSITION_APPROVED_NOT);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("اتاق");
        product.setName("روکش صندلی 405");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setPosition(Constant.POSITION_APPROVED_NOT);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setPosition(Constant.POSITION_APPROVED_NOT);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setPosition(Constant.POSITION_APPROVED_NOT);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setPosition(Constant.POSITION_APPROVED_NOT);
        products.add(product);

        iaView.showProducts(products);
    }
}