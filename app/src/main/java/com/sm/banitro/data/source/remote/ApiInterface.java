package com.sm.banitro.data.source.remote;

import com.sm.banitro.data.model.basic.BaseResponse;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.model.register.RegisterResponse;
import com.sm.banitro.data.model.seller.Seller;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface ApiInterface {

    // ********************************************************************************
    // Product

    @GET("seller/{seller_id}/{position}")
    Call<ArrayList<Product>> getProducts(@Path("seller_id") String sellerId,
                                         @Path("position") String condition);

    @GET("delete/{reply_id}/{seller_id}")
    Call<BaseResponse> getProductForDelete(@Path("reply_id") String productId,
                                           @Path("seller_id") String sellerId);

    // ********************************************************************************
    // Reply

    @POST("reply/suggest")
    @FormUrlEncoded
    Call<BaseResponse> postReplySuggest(@Field("reply_price") String replyPrice,
                                        @Field("reply_dc") String replyDc,
                                        @Field("reply_id") String productId);

    @POST("reply/edit")
    @FormUrlEncoded
    Call<BaseResponse> postReplyEdit(@Field("reply_price") String replyPrice,
                                     @Field("reply_dc") String replyDc,
                                     @Field("reply_id") String productId);

    // ********************************************************************************
    // Seller

    @GET("seller/id/{seller_id}")
    Call<Seller> getSeller(@Path("seller_id") String sellerId);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileImage(@Field("id") String sellerId,
                                        @Field("user_pic") String image);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileName(@Field("id") String sellerId,
                                       @Field("full_name") String name);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfilePhoneNumber(@Field("id") String sellerId,
                                              @Field("user_phone") String phoneNumber);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileAddress(@Field("id") String sellerId,
                                          @Field("user_address") String address);

    @POST("user/edit")
    @FormUrlEncoded
    Call<BaseResponse> postProfileCategory(@Field("id") String sellerId,
                                           @Field("user_category") String category);

    @POST("user/register")
    @FormUrlEncoded
    Call<RegisterResponse> postRegisterInfo(@Field("full_name") String name,
                                            @Field("user_phone") String phoneNamber,
                                            @Field("user_address") String address,
                                            @Field("user_category") String categories,
                                            @Field("user_pass") String password);

    @POST("user/login")
    @FormUrlEncoded
    Call<RegisterResponse> postLoginInfo(@Field("username") String username,
                                         @Field("password") String password);
}