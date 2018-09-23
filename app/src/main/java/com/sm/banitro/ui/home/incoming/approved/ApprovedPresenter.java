package com.sm.banitro.ui.home.incoming.approved;

import android.content.Context;

import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.model.Reply;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.util.Constant;

import java.util.ArrayList;

public class ApprovedPresenter implements ApprovedContract.Presenter {
    private ApprovedContract.View iaView;
//    private Repository repository;

    public ApprovedPresenter(ApprovedContract.View iaView, Context context) {
        this.iaView = iaView;
//        repository = new Repository(context);
    }

    @Override
    public void loadData() {
//        repository.loadProducts(Constant.POSITION_APPROVED,
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
        Reply reply=new Reply();
        category.setName("عقب");
        product.setName("صندوق عقب 206");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(12000);
        product.setReply(reply);
        product.setPosition(Constant.POSITION_APPROVED);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(155000);
        product.setReply(reply);
        product.setPosition(Constant.POSITION_APPROVED);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(67540000);
        product.setReply(reply);
        product.setPosition(Constant.POSITION_APPROVED);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(120050);
        product.setReply(reply);
        product.setPosition(Constant.POSITION_APPROVED);
        products.add(product);

        iaView.showProducts(products);
    }
}