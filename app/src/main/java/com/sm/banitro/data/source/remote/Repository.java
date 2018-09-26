package com.sm.banitro.data.source.remote;

import android.content.Context;

import com.sm.banitro.data.model.DeleteResponse;
import com.sm.banitro.data.model.Product;
import com.sm.banitro.data.source.local.AppPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ApiInterface apiInterface;
    private AppPreferences pref;

    public Repository(Context context) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pref = new AppPreferences(context);
    }

    public void loadProducts(int position, final ApiResult<ArrayList<Product>> callback) {
        Call<ArrayList<Product>> call = apiInterface.getProducts(pref.getSellerId(), position);
        call.enqueue(new Callback<ArrayList<Product>>() {

            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void deleteProduct(Product product, final ApiResult<DeleteResponse> callback) {
        final Call<DeleteResponse> call = apiInterface.postProductForDelete(product.getId());
        call.enqueue(new Callback<DeleteResponse>() {

            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }
}