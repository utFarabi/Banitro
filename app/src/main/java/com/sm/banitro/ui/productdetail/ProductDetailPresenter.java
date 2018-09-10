package com.sm.banitro.ui.productdetail;

import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;

public class ProductDetailPresenter implements ProductDetailContract.Presenter {
    private ProductDetailContract.View iaView;
    private Repository repository;

    public ProductDetailPresenter(ProductDetailContract.View iaView) {
        this.iaView = iaView;
        repository = new Repository();
    }

    @Override
    public void loadData(int productId) {
        iaView.showProgress();
        repository.loadProduct(productId, new ApiResult<Product>() {

            @Override
            public void onSuccess(Product result) {
                iaView.hideProgress();
                iaView.showProduct(result);
            }

            @Override
            public void onFail(String errorMessage) {
                iaView.hideProgress();
                iaView.showErrorMessage(errorMessage);
            }
        });
    }
}