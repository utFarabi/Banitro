package com.sm.banitro.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.sm.banitro.R;
import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.model.register.RegisterResponse;
import com.sm.banitro.data.model.seller.Seller;
import com.sm.banitro.data.source.local.AppPreferences;
import com.sm.banitro.util.ConstantUtil;

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
    private static Repository INSTANCE;
    // ********************************************************************************
    // Constructor

    public Repository(Context context) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pref = new AppPreferences(context);
    }

    public static Repository getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }


    // ********************************************************************************
    // Method

    public void loadProducts(String condition, final ApiResult<ArrayList<Product>> callback) {
        Call<ArrayList<Product>> call = apiInterface.getProducts(pref.getSellerId(), condition, ConstantUtil.TOKEN);
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
        final Call<BaseResponse> call = apiInterface.getProductForDelete(product.getId(), product.getSellerId(), ConstantUtil.TOKEN);
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
        final Call<Seller> call = apiInterface.getSeller(pref.getSellerId(), ConstantUtil.TOKEN);
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
            call = apiInterface.postReplyEdit(price, description, product.getId(), ConstantUtil.TOKEN);
        } else {
            call = apiInterface.postReplySuggest(price, description, product.getId(), ConstantUtil.TOKEN);
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
                call = apiInterface.postProfileName(pref.getSellerId(), text, ConstantUtil.TOKEN);
                break;
            case R.string.phone:
                call = apiInterface.postProfilePhoneNumber(pref.getSellerId(), text, ConstantUtil.TOKEN);
                break;
            case R.string.address:
                call = apiInterface.postProfileAddress(pref.getSellerId(), text, ConstantUtil.TOKEN);
                break;
            case R.string.categories:
                call = apiInterface.postProfileCategory(pref.getSellerId(), text, ConstantUtil.TOKEN);
                break;
            case R.string.choose_your_profile_image:
                call = apiInterface.postProfileImage(pref.getSellerId(), text, ConstantUtil.TOKEN);
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
                                 final ApiResult<RegisterResponse> callback) {
        Call<RegisterResponse> call = apiInterface.postRegisterInfo(name, phoneNamber, address, categories, password, ConstantUtil.TOKEN);
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("sina", "onSuccess: <<<<    fetchListOfCategories    >>>> with :" +
                        " success code = [" + response + "]" + ", message = [" + response.message() + "]");
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.i("sina", "onFailure");
                callback.onFail(t.getMessage());
            }
        });
    }

    public void sendLoginInfo(String username, String password, final ApiResult<RegisterResponse> callback) {
        Log.i("sina", "username: " + username + "     password: " + password);
        Call<RegisterResponse> call = apiInterface.postLoginInfo(username, password, ConstantUtil.TOKEN);
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("sina", "onSuccess: <<<<    fetchListOfCategories    >>>> with :" +
                        " success code = [" + response + "]" + ", message = [" + response.message() + "]");
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Log.i("sina", "onFailure");
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }
}