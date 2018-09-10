package com.sm.banitro.data.source.remote;

import com.sm.banitro.data.model.Demand;
import com.sm.banitro.data.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ApiInterface apiInterface;

    public Repository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void loadDemands(final ApiResult<ArrayList<Demand>> callback) {
        Call<ArrayList<Demand>> call = apiInterface.getDemands();
        call.enqueue(new Callback<ArrayList<Demand>>() {
            @Override
            public void onResponse(Call<ArrayList<Demand>> call, Response<ArrayList<Demand>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Demand>> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void loadProduct(int productId, final ApiResult<Product> callback) {
        Call<Product> call = apiInterface.getProduct(productId);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }
}