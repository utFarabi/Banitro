package com.sm.banitro.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.sm.banitro.R;
import com.sm.banitro.data.model.IdResponse;
import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.model.seller.Seller;
import com.sm.banitro.data.source.local.AppPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    // ********************************************************************************
    // Field

    // Instance
    private ApiInterface apiInterface;
    private AppPreferences pref;

    // ********************************************************************************
    // Constructor

    public Repository(Context context) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pref = new AppPreferences(context);
    }

    // ********************************************************************************
    // Method

    public void loadProducts(String condition, final ApiResult<ArrayList<Product>> callback) {
        Call<ArrayList<Product>> call = apiInterface.getProducts(pref.getSellerId(), condition);
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

    public void deleteProduct(Product product, final ApiResult<BaseResponse> callback) {
        final Call<BaseResponse> call = apiInterface.getProductForDelete(product.getId(), product.getSellerId());
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void loadSeller(final ApiResult<Seller> callback) {
        final Call<Seller> call = apiInterface.getSeller(pref.getSellerId());
        call.enqueue(new Callback<Seller>() {

            @Override
            public void onResponse(Call<Seller> call, Response<Seller> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Seller> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void sendReply(Product product, String price, String description,
                          final ApiResult<BaseResponse> callback) {

        final Call<BaseResponse> call;

        if (product.isReplied()) {
            call = apiInterface.postReplyEdit(price, description, product.getId());
        } else {
            call = apiInterface.postReplySuggest(price, description, product.getId());
        }

        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void sendInfo(String text, int type, final ApiResult<BaseResponse> callback) {

        final Call<BaseResponse> call;

        switch (type) {
            case R.string.full_name:
                call = apiInterface.postProfileName(pref.getSellerId(), text);
                break;
            case R.string.phone:
                call = apiInterface.postProfilePhoneNumber(pref.getSellerId(), text);
                break;
            case R.string.address:
                call = apiInterface.postProfileAddress(pref.getSellerId(), text);
                break;
            case R.string.categories:
                call = apiInterface.postProfileCategory(pref.getSellerId(), text);
                break;
            default:
                call = null;
        }

        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public void sendRegisterInfo(String name, String phoneNamber, String address, String categories, String password,
                                 final ApiResult<String> callback) {

        Call<IdResponse> call = apiInterface.postRegisterInfo(name, phoneNamber, address, /*categories*/"1012", password);
        call.enqueue(new Callback<IdResponse>() {

            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                Log.i("sina","onResponse");
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getId());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                Log.i("sina","onFailure");
                callback.onFail(t.getMessage());
            }
        });
    }

    public void sendLoginInfo(String username, String password, final ApiResult<String> callback) {

        Call<IdResponse> call = apiInterface.postLoginInfo(username, password);
        call.enqueue(new Callback<IdResponse>() {

            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getId());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }
}