package com.sm.banitro.ui.recent;

import android.content.Context;

import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.util.Constant;

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

    private void test() {
        ArrayList<Product> products=new ArrayList<>();

        Product product=new Product();
        Category category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        product=new Product();
        category=new Category();
        category.setName("جلوبندی");
        product.setName("آینه جلوی پراید");
        product.setCategory(category);
        product.setNumber(2);
        product.setReplied(false);
        products.add(product);

        iaView.showProducts(products);
    }
}