package com.sm.banitro.ui.home.recent;

import android.content.Context;

import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.DeleteResponse;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.model.Reply;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

import java.util.ArrayList;

public class RecentPresenter implements RecentContract.Presenter {
    private RecentContract.View iaView;
//    private Repository repository;

    public RecentPresenter(RecentContract.View iaView, Context context) {
        this.iaView = iaView;
//        repository = new Repository(context);
    }

    @Override
    public void loadData() {
//        iaView.showProgress();
//        repository.loadProducts(Constant.POSITION_RECENT,
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

    @Override
    public void deleteProduct(final Product product) {
//        iaView.showProgress();
//        repository.deleteProduct(product, new ApiResult<DeleteResponse>() {
//
//            @Override
//            public void onSuccess(DeleteResponse result) {
//                iaView.hideProgress();
//                if (result.isDeleted()) {
//                    iaView.productDeleted(product);
//                }
//            }
//
//            @Override
//            public void onFail(String errorMessage) {
//                iaView.hideProgress();
//                iaView.showErrorMessage(errorMessage);
//            }
//        });
        test2(product);
    }

    private void test() {
        ArrayList<Product> products = new ArrayList<>();

        Product product = new Product();
        Category category = new Category();
        Reply reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 0");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setId(0);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("عقب");
        product.setName("صندوق عقب 1");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(12000);
        product.setReply(reply);
        product.setId(1);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("اتاق");
        product.setName("روکش صندلی 2");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setId(2);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 3");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setId(3);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 4");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(155000);
        product.setReply(reply);
        product.setId(4);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 5");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(67540000);
        product.setReply(reply);
        product.setId(5);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 6");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setId(6);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 7");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        product.setId(7);
        products.add(product);

        product = new Product();
        category = new Category();
        reply = new Reply();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید 8");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(true);
        reply.setPrice(120050);
        product.setReply(reply);
        product.setId(8);
        products.add(product);

        iaView.showProducts(products);
    }

    private void test2(Product product) {
        iaView.productDeleted(product);
    }
}